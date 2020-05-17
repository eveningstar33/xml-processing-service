package com.sacom.integrateservice.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.sacom.integrateservice.model.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FileHandler implements MessageHandler {

    public static final String NON_DIGIT_REGEX_PATTERN = "\\D+";
    public static final String OUTPUT_LOCATION = "D:\\outputLocation\\";
    public static final String XML_TYPE_OF_FILE = ".xml";

    public void handleMessage(Message<?> mesg) throws MessagingException {
        File file = (File) mesg.getPayload();
        String orderNo = file.getName().replaceAll(NON_DIGIT_REGEX_PATTERN,"");
        List<ProductOutput> productsList = new ArrayList<>();
        Set<String> noDupSet = new HashSet<>();
        Orders orders = parseXmlToProduct(file);
        List<Order> ordersList = orders.getOrders();

        for (Order order : ordersList) {
//            System.out.println("ID : " + order.getId());
//            System.out.println("Created : " + order.getCreated());
            for (Product product : order.getProducts()) {
//                System.out.println("Product : " + "gtin -> " + product.getGtin() + ", description -> "
//                        + product.getDescription() + ", price -> " + product.getPrice().getValue() +
//                        ", supplier -> " + product.getSupplier());
                ProductOutput productOutput = new ProductOutput(product.getGtin(), product.getDescription(),
                        product.getPrice(), product.getSupplier(), order.getCreated(), order.getId());

                productsList.add(productOutput);
                noDupSet.add(product.getSupplier());
            }
        }
        productToXml(productsList, noDupSet, orderNo);
    }

    private Orders parseXmlToProduct(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Orders.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Orders orders = (Orders) jaxbUnmarshaller.unmarshal(file);

            return orders;
        } catch (JAXBException e) {
            throw new RuntimeException("Could not unmarshall workflow xml", e);
        }
    }

    private void productToXml(List<ProductOutput> productsList, Set<String> noDupSet, String orderNo) {
        for (String retailer : noDupSet) {
            String fileName = MessageFormat
                    .format("{0}{1}{2}{3}", OUTPUT_LOCATION, retailer, orderNo, XML_TYPE_OF_FILE);

            File file = new File(fileName);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ProductsOutput.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                List<ProductOutput> retailList = productsList.stream()
                        .filter(e -> e.getSupplier().equals(retailer))
                        .sorted(Comparator.comparing((ProductOutput e) -> e.getDate())
                                .thenComparing((ProductOutput e) -> e.getPrice().getValue()).reversed())
                        .collect(Collectors.toList());

                ProductsOutput products = new ProductsOutput(retailList);
                jaxbMarshaller.marshal(products, file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}