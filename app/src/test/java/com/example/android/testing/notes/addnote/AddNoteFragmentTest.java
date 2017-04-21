package com.example.android.testing.notes.addnote;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.view.menu.ActionMenuItem;
import android.view.MenuItem;
import com.example.android.testing.notes.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowPackageManager;

import static com.example.android.testing.notes.addnote.AddNoteFragment.REQUEST_CODE_IMAGE_CAPTURE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AddNoteFragmentTest {

  private AddNoteActivity addNoteActivity;
  private AddNoteFragment addNoteFragment;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    addNoteActivity = Robolectric.setupActivity(AddNoteActivity.class);
    addNoteFragment = (AddNoteFragment) addNoteActivity.getSupportFragmentManager().findFragmentById(R.id.contentFrame);
  }

  @Test
  public void showNotesList_shouldFinishActivity() throws Exception {
    addNoteFragment.showNotesList();

    assertThat(addNoteActivity.isFinishing()).isTrue();
    assertThat(shadowOf(addNoteActivity).getResultCode()).isEqualTo(Activity.RESULT_OK);
  }

  @Test
  public void whenTakePictureMenuSelected_shouldLaunchImageCapture() throws Exception {
    addResolveInfoForImageCapture();

    MenuItem menuItem = new ActionMenuItem(addNoteActivity, 0, R.id.take_picture, 0, 0, null);
    addNoteFragment.onOptionsItemSelected(menuItem);

    ShadowActivity.IntentForResult startedIntent = shadowOf(addNoteActivity).getNextStartedActivityForResult();
    assertThat(startedIntent.intent.getAction()).isEqualTo(MediaStore.ACTION_IMAGE_CAPTURE);
    assertThat(startedIntent.requestCode & 0xffff).isEqualTo(REQUEST_CODE_IMAGE_CAPTURE);
    assertThat(startedIntent.intent.getExtras().get(MediaStore.EXTRA_OUTPUT)).isInstanceOf(Uri.class);
  }

  private void addResolveInfoForImageCapture() {
    ShadowPackageManager shadowPackageManager = shadowOf(addNoteActivity.getPackageManager());
    ResolveInfo resolveInfo = new ResolveInfo();
    resolveInfo.activityInfo = new ActivityInfo();
    resolveInfo.activityInfo.applicationInfo = new ApplicationInfo();
    resolveInfo.activityInfo.applicationInfo.packageName = addNoteActivity.getPackageName();
    resolveInfo.activityInfo.name = AddNoteActivity.class.getName();
    shadowPackageManager.addResolveInfoForIntent(
        new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
        resolveInfo);
  }
}