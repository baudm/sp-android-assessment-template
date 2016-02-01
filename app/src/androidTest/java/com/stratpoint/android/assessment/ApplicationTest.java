package com.stratpoint.android.assessment;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public ApplicationTest() {
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started.
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished.
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}

	public void testAddNote() throws Exception {
		//Unlock the lock screen
		solo.unlockScreen();
		solo.clickOnMenuItem("Add note");
		//Assert that NoteEditor activity is opened
		solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor");
		//In text field 0, enter Note 1
		solo.enterText(0, "Note 1");
		solo.goBack();
		//Clicks on menu item
		solo.clickOnMenuItem("Add note");
		//In text field 0, type Note 2
		solo.typeText(0, "Note 2");
		//Go back to first activity
		solo.goBack();
		//Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
		solo.takeScreenshot();
		boolean notesFound = solo.searchText("Note 1") && solo.searchText("Note 2");
		//Assert that Note 1 & Note 2 are found
		assertTrue("Note 1 and/or Note 2 are not found", notesFound);
	}

	public void testEditNote() throws Exception {
		//Long clicks on the second list line
		solo.clickLongInList(2);
		//Change title
		solo.clickOnText("Edit title");
		//Hides the soft keyboard
		solo.hideSoftKeyboard();
		//Change orientation of activity
		solo.setActivityOrientation(Solo.LANDSCAPE);
		//In first text field (0), add test
		solo.enterText(0, " test");
		solo.setActivityOrientation(Solo.PORTRAIT);
		//(Regexp) case insensitive
		boolean noteFound = solo.waitForText("(?i).*?note 1 test");
		//Assert that Note 1 test is found
		assertTrue("Note 1 test is not found", noteFound);
	}

	public void testRemoveNote() throws Exception {
		//(Regexp) case insensitive/text that contains "test"
		solo.clickOnText("(?i).*?test.*");
		//Delete Note 1 test
		solo.clickOnMenuItem("Delete");
		//Note 1 test should not be found
		boolean noteFound = solo.searchText("Note 1 test");
		//Assert that Note 1 test is not found
		assertFalse("Note 1 Test is found", noteFound);
		solo.clickLongOnText("Note 2");
		//Clicks on Delete in the context menu
		solo.clickOnText("Delete");
		//Will wait 100 milliseconds for the text: "Note 2"
		noteFound = solo.waitForText("Note 2", 1, 100);
		//Assert that Note 2 is not found
		assertFalse("Note 2 is found", noteFound);
	}
}