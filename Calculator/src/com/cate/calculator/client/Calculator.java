package com.cate.calculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Window;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class Calculator implements EntryPoint {
private final String H_TEXT = "gwtCalc" ;
private final String COMMON_WIDTH = "130px" ;
private Label header = new Label();
private TextBox displayArea = new TextBox();
private FlexTable numTable = new FlexTable();
private FlexTable clearsTable = new FlexTable();
private VerticalPanel mainPanel = new VerticalPanel();
private String[] syms = new String[] { "/" , "x" , "-" , "+" , "=" };
private String[] clears = new String[] { "c" , "ce" , "%" };
private String[] botRow = new String[] { "." , "0" , "+-" };
private ArrayList< String> eqParts = new ArrayList< String> ();
public void onModuleLoad() {

// Create the buttons
int row = 0 ;
int column = 0 ;
for ( int i = 1 ; i < 16; i ++ ) {
Button btn = null;
if (row == 0 ) {
final String value = String. valueOf(i);
btn = new Button(value);
btn .addClickListener( new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(value);
}
});
} else if (row == 3 ) {
if (i != 16) {
final String value = botRow[column];
btn = new Button(value);
btn . addClickListener( new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(value);
}
});
}
} else if (column == 3 ) {
btn = null;
} else {
int adjusted = i - row;
final String value = String. valueOf(adjusted);
btn = new Button(value);
btn .addClickListener( new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(value);
}
});
}
numTable. setWidget(row, column, btn);
column++ ;
if ((i % 4 ) == 0) {

// go to a new row, and go back to first column
row ++ ;
column = 0 ;
}
}

// Manually add the symbols (operators) and clear sets
clearsTable . setWidget( 0 , 0, new Button(clears[ 0 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(clears[ 0 ]);
}
}));
clearsTable . setWidget( 0 , 1, new Button(clears[ 1 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(clears[ 1 ]);
}
}));
clearsTable . setWidget( 0 , 2, new Button(clears[ 2 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(clears[ 2 ]);
}
}));
clearsTable . setWidget( 0 , 3, new Button(syms[ 0 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(syms[ 0 ]);
}
}));
numTable. setWidget( 0 , 3 , new Button(syms[ 1 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(syms[ 1 ]);
}
}));
numTable. setWidget( 1 , 3 , new Button(syms[ 2 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(syms[ 2 ]);
}
}));
numTable. setWidget( 2 , 3 , new Button(syms[ 3 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(syms[ 3 ]);
}
}));
numTable. setWidget( 3 , 3 , new Button(syms[ 4 ], new ClickListener() {
public void onClick (Widget sender) {
addToDisplay(syms[ 4 ]);
}
}));
numTable. setWidth( COMMON_WIDTH );
displayArea . setWidth(COMMON_WIDTH );
header. setText( H_TEXT );
header. setWidth( COMMON_WIDTH );

// Construct the mainPanel in order (top to bottom)
mainPanel . add(header);
mainPanel . add(displayArea);
mainPanel . add(clearsTable);
mainPanel . add(numTable);

// add styles
mainPanel . addStyleName("gwt-VerticalPanel" );
mainPanel . addStyleName("calc" );

// add the mainPanel to the page
RootPanel . get( "calc" ) .add(mainPanel);
}
private void addToDisplay(String addText) {
String origText = new String();
origText = displayArea . getText();
if (ArrayContains(syms, addText)) {
if (addText == "=" ) {

// figure it out and clear the past
eqParts .add(origText);
FigureItOut();
eqParts .clear();
return;
} else // anything else
{

// add to array
if (displayArea . getText() != null)
eqParts . add(origText);
eqParts .add(addText);
displayArea .setText( "" );
return;
}
}
if (addText == "+-" ) {
if (origText . indexOf( "-" ) > - 1 )
displayArea .setText(origText . substring( 1 , origText . length()));
else
displayArea .setText( "-" + origText);
} else {
if (addText == "%" ) {
Double percent = null;
percent = Double . parseDouble(origText) / 100;
displayArea .setText(percent . toString());
return;
}
if (addText == "c" ) {
displayArea .setText( "" );
return;
}
if (addText == "ce" ) {
displayArea .setText( "" );
eqParts .clear();
return;
}
if (addText == "." ) {
if (origText . indexOf( "." ) > - 1 )
return;
else
displayArea . setText(origText + addText);
} else {
displayArea .setText(origText + addText);
}
}
}
private void FigureItOut () {

// set displayArea text to the answer
Double answer = null;
Double firstNum = null;
Double nextNum = null;
String operator = null;
for ( int i = 0 ; i < eqParts .size(); i ++ ) {
String currVal = eqParts . get(i);
boolean isOperator = false ;
isOperator = ArrayContains(syms, currVal);
// add a number
if (firstNum == null) {
firstNum = Double . parseDouble(currVal);
// Window.alert(firstNum.toString());
continue;
} else if (isOperator == true && i == 0 ) {
Window. alert( "Try again!" );
eqParts .clear();
return;
} else if (isOperator == true) {
if (i == eqParts .size()) {
Window. alert( "You may not end with an operator" );
eqParts . clear();
displayArea . setText( "" );
return;
}
operator = currVal;
// Window.alert(operator);
continue;
} else if (nextNum == null) {
nextNum = Double . parseDouble(currVal);
// Window.alert(nextNum.toString());
if (operator == "+" ) {
answer = firstNum + nextNum;
} else if (operator == "-" ) {
answer = firstNum - nextNum;
} else if (operator == "x" ) {
answer = firstNum * nextNum;
} else if (operator == "/" ) {
answer = firstNum / nextNum;
} else if (operator == "=" ) {
} 
else {
} 
displayArea .setText(answer . toString());
// Window.alert("The answer is " + answer);
firstNum = answer;
nextNum = null;
operator = null;
} else {
Window. alert( "Something unexpected happened. Try again." );
}
}
}
private boolean ArrayContains (String[] array , String check ) {
boolean found = false ;
for ( int i = 0 ; i < array .length; i ++ ) {
if (check == array[i])
return true;
}
return found;
}
}


