# YelpApiDemo

This repository is the yelp demo using 
- [retrofit2](https://github.com/square/retrofit) (do http connection)
- [rxandroid](https://github.com/ReactiveX/RxAndroid) (replace asynctask)
- [glide](https://github.com/bumptech/glide) (image load framwork)
- [signpost](https://github.com/pakerfeldt/okhttp-signpost) (sign okhttp client, yelp api need this)

In my most projects, I used 
- [async-http-client](https://github.com/AsyncHttpClient/async-http-client): but android sdk 23 abandon package org.apache.http, so I tried retrofit2, it is good one.
- regular android asynctask: rxjava is famous open source project from square, it can help code more clean and easier to read.
- [Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader): I use UIL for a long time, Im not sure if Glide is better then it

Refer:
- [cheesesquare](https://github.com/chrisbanes/cheesesquare)
- [yelp-android](https://github.com/Yelp/yelp-android)
