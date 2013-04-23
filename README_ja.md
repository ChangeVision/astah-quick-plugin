Quick Command プラグイン
===============================

![demo](https://raw.github.com/ChangeVision/astah-quick-plugin/master/docs/images/demo.gif)

バージョン
----------------
1.0.0

対象エディション
----------------
Astah Professional 6.6.4 or later

概要
----------------
このプラグインは複雑な操作を数ステップで完了できるQuick Windowを提供します。
Quick Windowには1行の入力フィールドがあり、予め定義されたコマンドを実行できます。
この入力フィールドではコマンドや、その引数をインクリメンタルサーチが行われたり、上下カーソルで選択でき、簡単に入力できます。

インストール方法
----------------
0. [JARファイルのダウンロード](http://astah.change-vision.com/plugins/quick/1.0.0.html)
1. astah*を起動
2. プラグイン一覧からインストールする ([ヘルプ]-[プラグイン一覧])
3. インストール後、再起動するとQuick Windowを表示するための設定画面が開かれます。

ビルド
------------
1. Astah Plug-in SDKをインストールします。 - <http://astah.change-vision.com/ja/plugins.html>
2. `git clone git://github.com/ChangeVision/astah-quick-plugin.git`
3. `cd script`
4. `astah-build`
5. `astah-launch`

 * Generating config to load classpath [for Eclipse](http://astah.change-vision.com/ja/plugin-tutorial/helloworld.html#eclipse)

   * `astah-mvn eclipse:eclipse`

ライセンス
---------------
Copyright 2013 Change Vision, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

サードパーティライセンス
------------------------
 * MiGLayoutは修正BSDライセンスです。同梱するライセンスファイルを参照してください。
