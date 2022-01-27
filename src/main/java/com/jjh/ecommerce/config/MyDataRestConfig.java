package com.jjh.ecommerce.config;

import com.jjh.ecommerce.entity.Product;
import com.jjh.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired//Autowire JPA entity manager
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable HTTP methods for Products: PUT, POST and DELETE
        //GET Method만 working 할 것. So READ ONLY
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
        //lock down those two repository to only make them read only..
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        //call an internal helper method
        exposeIds(config);
    }
    private void exposeIds(RepositoryRestConfiguration config){
        //expose entity ids


        // -get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        /*
          System.out.println("entities "+entities);
          entities [Product, ProductCategory]
        */

        // -create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // -get the entity types for the entities
        for(EntityType tempEntityType: entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        //-expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);//엔티티 아이디 노출 시키는 설정 완료!
    }
}
