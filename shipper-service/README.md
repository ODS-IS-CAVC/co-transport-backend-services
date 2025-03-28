# Shipper Service - 荷主向けサービス

## 概要・目的
このサービスは、荷主向けの輸送計画管理を提供するマイクロサービスです。貨物情報の管理、輸送計画の作成・管理、一括処理などの機能を提供します。
以下、アーキテクチャ概要を示す。  
![](../docs/architecture.png)

本システムは[shipper-service](https://github.com/ODS-IS-CAVC/co-transport-backend-services/tree/main/shipper-service)です。
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
    cd co-transport-backend-services/shipper-service
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
- Postmanなどで `http://localhost:2200` にAPIをたたき、確認してください。

## 設計標準
### システム連携
  - RESTful APIの提供
  - 外部システムとの連携
  - データ同期処理

### セキュリティ
  - ユーザー認証・認可
  - データアクセス制御
  - 監査ログ管理

### アーキテクチャ設計
- レイヤードアーキテクチャの採用
  - Presentation Layer: API エンドポイント
  - Application Layer: ビジネスロジック
  - Domain Layer: ドメインモデル
  - Infrastructure Layer: データアクセス、外部サービス連携

## コーディング規約
- コーディングスタイルは`Google Java Style Guide`に従う
- 開発は`develop`ブランチで行い、安定版は`main`ブランチにマージ

## 処理概要
サービスは以下の主要な処理を提供します：
- 輸送計画の管理処理
- 貨物情報の管理処理
- 輸送計画アイテムの管理処理
- ファイル操作処理

## 問合せ及び要望に関して
- 本リポジトリは現状は主に配布目的の運用となるため、IssueやPull Requestに関しては受け付けておりません。

## ライセンス
- このプロジェクトは [MITライセンス](../LICENSE.txt) のもとで公開されています。  
- 特筆が無い限り、ソースコードおよび関連ドキュメントの著作権はNEXT Logistics Japan株式会社に帰属します。

## 免責事項
- 本リポジトリの内容は予告なく変更・削除する可能性があります。
- 本リポジトリの利用により生じた損失及び損害等について、いかなる責任も負わないものとします。