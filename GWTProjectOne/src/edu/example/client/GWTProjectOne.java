package edu.example.client;

import java.io.IOException;
import java.util.logging.*;

import edu.example.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.jjs.impl.GwtAstBuilder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTProjectOne implements EntryPoint {
	
	private TextBox txtInput;
	private static Logger logger = Logger.getLogger(GWTProjectOne.class.getName());
	
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		logger.log(Level.INFO, "This is a log.");
		logger.setLevel(Level.INFO);
		

		VerticalPanel vPanel = new VerticalPanel();
		
		txtInput = new TextBox();
		txtInput.setText("Default text");
		
		Button but = new Button("Click");
		but.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String s = txtInput.getText();
				logger.info(s);
			}
		});
		
		vPanel.add(txtInput);
		vPanel.add(but);
		
		RootPanel.get().add(vPanel);
		
	}
}
