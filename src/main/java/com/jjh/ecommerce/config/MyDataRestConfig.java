package com.jjh.ecommerce.config;

import com.jjh.ecommerce.dao.entity.Country;
import com.jjh.ecommerce.dao.entity.Product;
import com.jjh.ecommerce.dao.entity.ProductCategory;
import com.jjh.ecommerce.dao.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${allowed.origins}")//application.properties 파일에서 해당 값 주입해준다.
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired//Autowire JPA entity manager
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST,
                                              HttpMethod.DELETE, HttpMethod.PATCH};

        //disable HTTP methods for Products: PUT, POST and DELETE, 즉 GET Method만 working 할 것. So READ ONLY
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);

        //call an internal helper method
        exposeIds(config);

        //configure CORS mapping
        //accept calls from web browser scripts for this origin(protocol+hostname+port)
        //여기에 이렇게 설정해주었기 때문에 Repository에서 @CrossOrigin 제거해도 된다.
        cors.addMapping(config.getBasePath()+"/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
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
        for(EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        //-expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);//엔티티 아이디 노출 시키는 설정 완료!
    }
}
