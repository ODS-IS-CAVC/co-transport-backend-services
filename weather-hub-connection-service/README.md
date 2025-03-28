# Weather Hub Connection Service - APIゲートウェイサービス

## 概要・目的
このサービスは、外部システムとの連携を管理するAPIゲートウェイサービスです。モビリティハブ、車両追跡、インシデント管理などの機能を提供し、システム間の通信を一元化します。
以下、アーキテクチャ概要を示す。  
![](../docs/architecture.png)

本システムは[weather-hub-connection-service](https://github.com/ODS-IS-CAVC/co-transport-backend-services/tree/main/weather-hub-connection-service)です。
## 前提環境
- Java 17 以上 24 以下
- Maven 3.9.7

## ビルド・起動手順
1. リポジトリをクローンします。
    ```bash
    git clone https://github.com/ODS-IS-CAVC/co-transport-backend-services.git
    ```
2. プロジェクトディレクトリに移動します。
    ```bash
    cd co-transport-backend-services/weather-hub-connection-service
    ```
3. [共通ライブラリー](https://github.com/ODS-IS-CAVC/co-transport-backend-common.git)のREADME.mdに記載されている手順に従って共通ライブリーをイントールします。

4. 依存関係をインストールし、ビルドします。
    ```bash
    mvn clean install
    ```

5. サーバーを起動します。
   例：
    ```bash
      mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

## テスト手順
- Postmanなどで `http://localhost:8400` にAPIをたたき、確認してください。

## 設計標準
### 主な機能
- モビリティハブ管理
  - モビリティハブの予約
  - 予約のキャンセル

- セミダイナミック情報管理
  - 動的情報の取得
  - 動的情報の更新

- 車両追跡 (Track by SIP)
  - 新規追跡情報の作成
  - 追跡情報の更新

- 車両インシデント管理
  - インシデント情報の登録
  - インシデント状態の監視

## コーディング規約
- コーディングスタイルは`Google Java Style Guide`に従う
- 開発は`develop`ブランチで行い、安定版は`main`ブランチにマージ

## 処理概要
サービスは以下の主要な処理を提供します：
- モビリティハブ予約処理
- 動的情報管理処理
- 車両追跡処理
- インシデント管理処理

## 問合せ及び要望に関して
- 本リポジトリは現状は主に配布目的の運用となるため、IssueやPull Requestに関しては受け付けておりません。

## ライセンス
- このプロジェクトは [MITライセンス](../LICENSE.txt) のもとで公開されています。   
- 特筆が無い限り、ソースコードおよび関連ドキュメントの著作権はNEXT Logistics Japan株式会社に帰属します。

## 免責事項
- 本リポジトリの内容は予告なく変更・削除する可能性があります。
- 本リポジトリの利用により生じた損失及び損害等について、いかなる責任も負わないものとします。