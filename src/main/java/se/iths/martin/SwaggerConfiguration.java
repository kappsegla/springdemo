//package se.iths.martin;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.plugin.core.SimplePluginRegistry;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfiguration {
//
//    @Bean
//    public LinkDiscoverers discoverers() {
//        List<LinkDiscoverer> plugins = new ArrayList<>();
//        plugins.add(new CollectionJsonLinkDiscoverer());
//        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//    }
//
//    //    @Bean
////    public Docket petApi() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.any())
////                .paths(PathSelectors.any())
////                .build()
////                .pathMapping("/")
////                .directModelSubstitute(LocalDate.class, String.class)
////                .genericModelSubstitutes(ResponseEntity.class)
//////                .alternateTypeRules(
//////                        newRule(typeResolver.resolve(DeferredResult.class,
//////                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//////                                typeResolver.resolve(WildcardType.class)))
////                .useDefaultResponseMessages(false)
////                .globalResponseMessage(RequestMethod.GET,
////                        newArrayList(new ResponseMessageBuilder()
////                                .code(500)
////                                .message("500 message")
////                                .responseModel(new ModelRef("Error"))
////                                .build()))
////                .enableUrlTemplating(true)
//////                .globalOperationParameters(
//////                        newArrayList(new ParameterBuilder()
//////                                .name("someGlobalParameter")
//////                                .description("Description of someGlobalParameter")
//////                                .modelRef(new ModelRef("string"))
//////                                .parameterType("query")
//////                                .required(true)
//////                                .build()))
////                //.tags(new Tag("Person Service", "All apis relating to persons"))
////                //.additionalModels(typeResolver.resolve(AdditionalModel.class))
////                ;
////    }
////
////    @Bean
////    UiConfiguration uiConfig() {
////        return UiConfigurationBuilder.builder()
////                .deepLinking(true)
////                .displayOperationId(false)
////                .defaultModelsExpandDepth(1)
////                .defaultModelExpandDepth(1)
////                .defaultModelRendering(ModelRendering.EXAMPLE)
////                .displayRequestDuration(false)
////                .docExpansion(DocExpansion.NONE)
////                .filter(false)
////                .maxDisplayedTags(null)
////                .operationsSorter(OperationsSorter.ALPHA)
////                .showExtensions(false)
////                .tagsSorter(TagsSorter.ALPHA)
////                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
////                .validatorUrl(null)
////                .build();
////    }
//}