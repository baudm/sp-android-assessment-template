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

	public void testScrolling() throws Exception {
		solo.unlockScreen();
		solo.assertCurrentActivity("Wrong activity", MainActivity.class);
		assertTrue(solo.searchText("The Shawshank Redemption", true));

		solo.scrollListToBottom(0);
		assertTrue(solo.searchText("The Good, the Bad and the Ugly", true));

		solo.setActivityOrientation(Solo.LANDSCAPE);
		assertTrue(solo.searchText("The Good, the Bad and the Ugly", true));

		solo.scrollListToTop(0);
		solo.setActivityOrientation(Solo.PORTRAIT);
		assertTrue(solo.searchText("The Shawshank Redemption", true));
	}

	public void testNavigation() throws Exception {
		solo.unlockScreen();
		solo.clickInList(0);
		solo.setActivityOrientation(Solo.LANDSCAPE);
		assertTrue(solo.searchText("The Shawshank Redemption", true));

		solo.goBack();
		solo.scrollListToBottom(0);
		solo.clickInList(3);
		solo.setActivityOrientation(Solo.PORTRAIT);
		assertTrue(solo.searchText("The Good, the Bad and the Ugly", true));
	}
}