# kotlin_jooq

## 実行方法
IDEのSDKはJava17
環境変数　JAVA_HOMEもJava17にpathを通しておく<br>
※Java11になっているとビルド通りません

Dockerを起動

```
docker-compose up -d
```


DBマイグレーション実行
```
./gradlew flywayMigrate
```
※本当はここでtableが作成されるはずなのですが、うまくいかなかったので、
コンテナの中のmysqlにアクセスしてsqlでtableを作成しました
```
docker container exec -it mysql_container bash
mysql -u root -p 

# passwprd : root

use library;
CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

```

JOOQコードの生成
```
./gradlew generateJooq
```

アプリケーションの実行
```
./gradlew bootRun
```

## エンドポイント
### 全取得
http://localhost:8080/api/books

### ＩＤ検索
http://localhost:8080/api/books/id

### authorでの検索
http://localhost:8080/api/books/search?author=xxx

### 更新
http://localhost:8080/api/books/id
```
{
    "title":"xxx",
    "author":"yyy"
}
```

### 追加
http://localhost:8080/api/books
```
{
    "title":"xxx",
    "author":"yyy"
}
```
