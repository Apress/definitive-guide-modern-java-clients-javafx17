package org.modernclient;

import org.modernclient.model.Person;
import org.modernclient.model.SampleData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {


    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private Button removeButton;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private ListView<Person> listView;

    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.

    private Person selectedPerson;
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Disable the Remove/Edit buttons if nothing is selected in the ListView control
        removeButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(firstnameTextField.textProperty().isEmpty()
                        .or(lastnameTextField.textProperty().isEmpty())));
        createButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNotNull()
                .or(firstnameTextField.textProperty().isEmpty()
                        .or(lastnameTextField.textProperty().isEmpty())));

        SampleData.fillSampleData(personList);

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<Person> sortedList = new SortedList<>(personList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) -> {
            int result = p1.lastname().compareToIgnoreCase(p2.lastname());
            if (result == 0) {
                result = p1.firstname().compareToIgnoreCase(p2.firstname());
            }
            return result;
        });
        listView.setItems(sortedList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("Selected item: " + newValue);
                    // newValue can be null if nothing is selected
                    selectedPerson = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        // Populate controls with selected Person
                        firstnameTextField.setText(selectedPerson.firstname());
                        lastnameTextField.setText(selectedPerson.lastname());
                        notesTextArea.setText(selectedPerson.notes());
                    } else {
                        firstnameTextField.setText("");
                        lastnameTextField.setText("");
                        notesTextArea.setText("");
                    }
                });

        // Pre-select the first item
        listView.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }

    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        System.out.println("Create");
        Person person = new Person(firstnameTextField.getText(),
                lastnameTextField.getText(), notesTextArea.getText());
        personList.add(person);
        // and select it
        listView.getSelectionModel().select(person);
    }

    @FXML
    private void removeButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove " + selectedPerson);
        personList.remove(selectedPerson);
    }

    @FXML
    private void updateButtonAction(ActionEvent actionEvent) {
        System.out.println("Update " + selectedPerson);
        Person person = new Person(firstnameTextField.getText(), lastnameTextField.getText(), notesTextArea.getText());
        personList.remove(listView.getSelectionModel().getSelectedItem());
        personList.add(person);
        listView.getSelectionModel().select(person);
        modifiedProperty.set(false);
    }
}
