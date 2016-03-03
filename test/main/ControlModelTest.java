package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import org.junit.Before;
import org.junit.Test;

import view.ControlPanel;

public class ControlModelTest {

	
	ControlPanel controlPanel;
	  @Before
	 public void executedBeforeEach() {
		
		  controlPanel = new ControlPanel();
	  }
	  	 
	@Test
	public void sortModelTest() {
		DefaultListModel model = new DefaultListModel<>();
		model.addElement("B");
		model.addElement("A");
		model.addElement("D");
		model.addElement("C");
		controlPanel.sortModel(model);
		List<String> expectedList = new ArrayList<>();
		expectedList.add("A");
		expectedList.add("B");
		expectedList.add("C");
		expectedList.add("D");
		List<String> outputList = new ArrayList<>();
		for (int i = 0; i < model.size(); i++) {
			outputList.add((String) model.get(i));
		}	
		assertEquals(expectedList, outputList);		
	}
	
	@Test
	public void modelTest() {
		DefaultComboBoxModel model = new DefaultComboBoxModel<>();
		model.addElement("B");
		model.addElement("A");
		model.addElement("D");
		model.addElement("C");
	
		controlPanel.sortModel(model);
		List<String> expectedList = new ArrayList<>();
		expectedList.add("A");
		expectedList.add("B");
		expectedList.add("C");
		expectedList.add("D");
		List<String> outputList = new ArrayList<>();
		for (int i = 0; i < model.getSize(); i++) {
			outputList.add((String) model.getElementAt(i));
		}	
		assertEquals(expectedList, outputList);		
	}
	
	
	

	
	
}
