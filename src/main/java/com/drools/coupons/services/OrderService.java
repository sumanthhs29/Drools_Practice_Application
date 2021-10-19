package com.drools.coupons.services;

import com.drools.coupons.dto.NewCoupon;
import com.drools.coupons.dto.NewOrder;
import com.drools.coupons.dto.Order;
import com.drools.coupons.entity.Coupon;
import com.drools.coupons.enums.Category;
import com.drools.coupons.repository.CouponRepository;
import org.drools.compiler.compiler.DecisionTableProvider;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final LocalDate curDate = LocalDate.now();

    private ArrayList<String> avaCoupons=new ArrayList<String>();

    private String couponIndicator="N";

    @Autowired
    CouponRepository couponRepository;

    public NewOrder applyCoupons(Order newOrder){

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        StatelessKieSession kSession = kContainer.newStatelessKieSession("ksession-rule");
        NewOrder myOrder = new NewOrder();
        kSession.setGlobal("curDate",curDate);
        kSession.setGlobal("avaCoupons",avaCoupons);
        newOrder.getProducts().stream().map(c->c.getCategory()==Category.BOOKS);
        kSession.execute(newOrder);
        myOrder.setProducts(newOrder.getProducts().stream().map(p->p.getItemName()).collect(Collectors.toList()));
        myOrder.setShippingDetails(newOrder.getShippingDetails());
        myOrder.setQuantity((int) newOrder.getProducts().stream().map(q->q.getQuantity()).count());
        myOrder.setTotalAmount(newOrder.getTotalAmount());
        myOrder.setDiscountPrice(newOrder.getDiscountPrice());
        myOrder.setMessage(avaCoupons +" Coupons applied");
        avaCoupons=new ArrayList<>();
        System.out.println("ORDER="+ myOrder);
        return myOrder;
    }

    public NewCoupon addingNewCoupons(Coupon coupon) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        StatelessKieSession kSession = kContainer.newStatelessKieSession("ksession-addingCoupon-rule");
        kSession.setGlobal("couponIndicator",couponIndicator);
        kSession.execute(coupon);
        NewCoupon newCoupon = new NewCoupon();
        if(coupon.getAmount()>=0 && coupon.getValue()>=0 && coupon.getValue()<=100){
            couponRepository.save(coupon);
            newCoupon.setCouponCode(coupon.getCouponName());
            newCoupon.setMessage(coupon.getCouponName()+" Added successfully, it is now open to avail");
            couponIndicator="N";
        }
        return newCoupon;
    }

    public void testSpreadSheet(Order order) {
//        KieServices ks = KieServices.Factory.get();
//        Resource dt
//                = ResourceFactory
//                .newClassPathResource("rules/discount.xls",
//                        getClass());
//
//        KieFileSystem kieFileSystem = ks.newKieFileSystem().write(dt);
//        KieBuilder kieBuilder = ks.newKieBuilder(kieFileSystem);
//        kieBuilder.buildAll();
//        KieRepository kieRepository = ks.getRepository();
//        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
//        KieContainer kieContainer
//                = ks.newKieContainer(krDefaultReleaseId);
//        UtilService utilService = new UtilService();
//        System.out.println(utilService.isCoupon(order.getCoupons(),"NEW50OFF"));
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        System.out.println(getDrlFromExcel("testing.xlsx"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/testing.xlsx"));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("allCoupon",order.getCoupons());
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    //Generating drl from spreadsheet
    public String getDrlFromExcel(String fileName) {
        DecisionTableConfiguration configuration =
                KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource("rules/" + fileName);
        DecisionTableProvider decisionTableProvider = new DecisionTableProviderImpl();

        return decisionTableProvider.loadFromResource(dt, configuration);
    }
}
