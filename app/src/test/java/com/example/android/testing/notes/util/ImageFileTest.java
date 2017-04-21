/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.notes.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for the implementation of {@link ImageFileImpl}.
 * <p>
 * The current Android tools support for writing unit tests is limited and requires mocking of all
 * Android dependencies in unit tests. That's why unit tests ideally should not have any
 * dependencies into android.jar, but sometimes they are inevitable. Usually using a wrapper class
 * or using a mocking framework like Mockito works fine, but there are situations where these
 * frameworks fall short, for instance when working with static util classes in the android.jar.
 *
 * <p>
 * To work around that limitation this test uses Powermockito, a library which adds support for
 * mocking static methods to Mockito. Powermockito should be used with care since it is normally a
 * sign of a bad code design. Nevertheless it can be handy while working with third party
 * dependencies, like the android.jar.
 */
@RunWith(RobolectricTestRunner.class)
public class ImageFileTest {

    private ImageFileImpl mImageFile;

    @Before
    public void createImageFile() throws Exception {
        // Get a reference to the class under test
        mImageFile = new ImageFileImpl();
    }

    @Test
    public void create_SetsImageFile() throws Exception {
        // When file helper is asked to create a file
        mImageFile.create("Name", "Extension");

        // Then the created file is stored inside the image file.
        assertThat(mImageFile.mFile, is(notNullValue()));
        assertThat(mImageFile.mFile.getName(), containsString("Name"));
        assertThat(mImageFile.mFile.getName(), containsString("Extension"));
    }

    @Test
    public void deleteImageFile() throws Exception {
        mImageFile.create("Name", "Extension");
        File file = mImageFile.mFile;

        // When file should be deleted
        mImageFile.delete();

        // Then stored file is deleted
        assertThat(file.exists(), is(false));
        assertThat(mImageFile.mFile, is(nullValue()));
    }
}
