package org.modernclients.propertiesandbindings;

public class JavaFXBeanMainExample {
    public static void main(String[] args) {
        JavaFXBeanModelExample model = new JavaFXBeanModelExample();
        JavaFXBeanViewExample view = new JavaFXBeanViewExample(model);
        JavaFXBeanControllerExample controller = new JavaFXBeanControllerExample(model, view);

        controller.incrementIPropertyOnModel();
        controller.changeStrPropertyOnModel();
        controller.switchColorPropertyOnModel();
        controller.incrementIPropertyOnModel();
        controller.changeStrPropertyOnModel();
        controller.switchColorPropertyOnModel();
    }
}
