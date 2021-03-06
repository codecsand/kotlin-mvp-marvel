# Kotlin MVP Marvel
 Kotlin MVP Architecture using coroutines and retrofit including room, no rxjava
 
**Project Functionality:**
*	Fetching the data from the REST API: https://developer.marvel.com/
*	The top items bar load the marvel heros names and their images and scroll in horizontal direction for paginated data.
*	The main page feed scroll in vertical direction and support paginated data which show all comics with their descriptions and load the data only once a day then store it for offline use.

## dependencies
* Room: androidx.room:room-runtime:2.3.0
* Retrofit2: com.squareup.retrofit2:retrofit:2.9.0
* GSON: com.squareup.retrofit2:converter-gson:2.9.0
* Work: androidx.work:work-runtime-ktx:1.5.0

## instructions
* Put the marvel private and public keys inside local.properties
```
marvel_private_key=value
marvel_public_key=value
```


![App Demo](https://github.com/mahmz/kotlin-mvp-coroutines/blob/main/demo/mvp_marvel.gif)



## MIT License
Copyright (c) 2021 mahmz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
