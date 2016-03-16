# CS263 Amazon Price Tracker

## API Authentication

There are two REST APIs exposed:
- wishlist
- sharedlist

Both API require user to login with their Google account first.
The following commands allow user to obtain the necessary cookie to access the REST APIs with curl. (Only applicable to development server)

```
curl http://localhost:8080/_ah/login \
  -d "email=test@example.com&action=Log+In" \
  -c -
```
  
Take the last value of the result "test@example.com:False:18580..." and use it to send the API request.

```
curl -XGET -H "Accept:application/json" http://localhost:8080/rest/wishlist \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

## Wishlist

Send GET to /rest/wishlist to get the wishlist of the user.

```
curl -XGET -H "Accept:application/json" http://localhost:8080/rest/wishlist \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

To POST a new product, the product JSON object needs the following field:
- productID: String
- productName: String
- currentPrice: Double
- lowestPrice: Double
- lowestDate: Date

```
curl -XPOST -H "Content-Type:application/json" \
  -d '{"productID":"XXXXXXXXXX", "productName":"Test product", "currentPrice":999.0, "lowestPrice":999.0, "lowestDate":1457993425165}' \
  http://localhost:8080/rest/wishlist \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

To DELETE a product, send DELETE request to /rest/wishlist/${product_id}.

```
curl -XDELETE http://localhost:8080/rest/wishlist/XXXXXXXXXX \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

## Sharedlist

Send GET to /rest/sharedlist to get the list of the shared products.

```
curl -XGET -H "Accept:application/json" http://localhost:8080/rest/sharedlist \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

To POST a new shared product, the product JSON object needs the following field:
- productID: String
- productName: String
- sharedDate: Date
The field `email` will be filled with the current user automatically.

```
curl -XPOST -H "Content-Type:application/json" \
  -d '{"productID":"XXXXXXXXXX", "productName":"Test product", "sharedDate":1457993425165}' \
  http://localhost:8080/rest/sharedlist \
  -b "dev_appserver_login="test@example.com:false:18580..."; Path=/;"
```

## Other internal servlets

There are some servlets are used internally only:
- mail
- cron/flush
- cron/update

Send POST to `/mail` to send a mail. Required parameters are `email`, `productName`, `productID`, and `price`. The content is automatically generated. (See MailServlet.java)

Send POST to `/cron/flush` to flush the memcache, and to `/cron/update` to update the products' prices. 