package com.example.android.testing.notes.notes;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.android.testing.notes.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class NotesFragmentTest {

  private NotesActivity notesActivity;
  private Fragment notesFragment;

  @Before
  public void setUp() throws Exception {
    notesActivity = Robolectric.setupActivity(NotesActivity.class);
    notesFragment = notesActivity.getSupportFragmentManager().findFragmentById(R.id.contentFrame);
  }

  @Test
  public void columnCountForPhone() throws Exception {
    GridLayoutManager layoutManager = getNotesListLayoutManager();
    assertThat(layoutManager.getSpanCount()).isEqualTo(2);
  }

  @Test @Config(qualifiers = "w820dp")
  public void columnCountForTablet() throws Exception {
    GridLayoutManager layoutManager = getNotesListLayoutManager();
    assertThat(layoutManager.getSpanCount()).isEqualTo(4);
  }

  private GridLayoutManager getNotesListLayoutManager() {
    RecyclerView notesList = (RecyclerView) notesFragment.getView().findViewById(R.id.notes_list);
    return (GridLayoutManager) notesList.getLayoutManager();
  }
}