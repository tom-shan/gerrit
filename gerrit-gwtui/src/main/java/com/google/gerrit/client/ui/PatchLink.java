// Copyright (C) 2008 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.client.ui;

import com.google.gerrit.client.Dispatcher;
import com.google.gerrit.client.changes.PatchTable;
import com.google.gerrit.common.data.PatchSetDetail;
import com.google.gerrit.reviewdb.Patch;

public abstract class PatchLink extends InlineHyperlink {
  protected Patch.Key patchKey;
  protected int patchIndex;
  protected PatchSetDetail patchSetDetail;
  protected PatchTable parentPatchTable;

  /**
   * @param text The text of this link
   * @param patchKey The key for this patch
   * @param patchIndex The index of the current patch in the patch set
   * @param historyToken The history token
   * @parma patchSetDetail Detailed information about the patch set.
   * @param parentPatchTable The table used to display this link
   */
  public PatchLink(final String text, final Patch.Key patchKey,
      final int patchIndex, final String historyToken,
      final PatchSetDetail patchSetDetail,
      final PatchTable parentPatchTable) {
    super(text, historyToken);
    this.patchKey = patchKey;
    this.patchIndex = patchIndex;
    this.patchSetDetail = patchSetDetail;
    this.parentPatchTable = parentPatchTable;
  }

  @Override
  public void go() {
    Dispatcher.patch( //
        getTargetHistoryToken(), //
        patchKey, //
        patchIndex, //
        patchSetDetail, //
        parentPatchTable //
        );
  }

  public static class SideBySide extends PatchLink {
    public SideBySide(final String text, final Patch.Key patchKey,
        final int patchIndex, PatchSetDetail patchSetDetail,
        PatchTable parentPatchTable) {
      super(text, patchKey, patchIndex, Dispatcher.toPatchSideBySide(patchKey),
          patchSetDetail, parentPatchTable);
    }
  }

  public static class Unified extends PatchLink {
    public Unified(final String text, final Patch.Key patchKey,
        final int patchIndex, PatchSetDetail patchSetDetail,
        PatchTable parentPatchTable) {
      super(text, patchKey, patchIndex, Dispatcher.toPatchUnified(patchKey),
          patchSetDetail, parentPatchTable);
    }
  }
}
