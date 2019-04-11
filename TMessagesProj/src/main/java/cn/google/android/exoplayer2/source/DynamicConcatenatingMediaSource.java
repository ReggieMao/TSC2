/*
 * Copyright (C) 2017 The Android Open Source Project
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
package cn.google.android.exoplayer2.source;

/** @deprecated Use {@link ConcatenatingMediaSource} instead. */
@Deprecated
public final class DynamicConcatenatingMediaSource extends ConcatenatingMediaSource {

  /**
   * @deprecated Use {@link ConcatenatingMediaSource#ConcatenatingMediaSource(MediaSource...)}
   *     instead.
   */
  @Deprecated
  public DynamicConcatenatingMediaSource() {}

  /**
   * @deprecated Use {@link ConcatenatingMediaSource#ConcatenatingMediaSource(boolean,
   *     MediaSource...)} instead.
   */
  @Deprecated
  public DynamicConcatenatingMediaSource(boolean isAtomic) {
    super(isAtomic);
  }

  /**
   * @deprecated Use {@link ConcatenatingMediaSource#ConcatenatingMediaSource(boolean, ShuffleOrder,
   *     MediaSource...)} instead.
   */
  @Deprecated
  public DynamicConcatenatingMediaSource(boolean isAtomic, ShuffleOrder shuffleOrder) {
    super(isAtomic, shuffleOrder);
  }
}
