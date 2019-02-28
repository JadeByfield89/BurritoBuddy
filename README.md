# BurritoBuddy
This is an Android app the uses the user's current location to display a list of nearby burrito places. It uses the MVP architectural pattern.

Notes for the reviewer

1) This was a really fun little app and I enjoyed working on it!

2) One issue I noticed was the in the demo/iOS screenshots provided, there was a short description field in the place cells. It looks like this field is no longer available via the Google Places API. Specifically, I was using the Nearby Search web service and this brief description was not available. 

I also looked at Place Details, to see if making another API call could get me that info. Unfortunately I could not find this field. If I missed it, please shoot me an email and I will go ahead and add it. 

https://stackoverflow.com/questions/44577246/google-places-api-place-description-summary leads me to believe that this field was deprecated and no longer avaialble via the API. But again if I missed it please let me know.

3) Regarding the JSON parsing, I went ahead and did it manually because the parsing was simple and this was a small project. In a larger production app, I would use a parsing libary like GSON, or even try Moshi which I've been wanting to test out.

4) It wasn't specificed whether to use Java or Kotlin for this project, so I went ahead and used Java since that's what I have the most experience with, but I've been picking up Kotlin lately and really enjoy it. Happy to rewrite it in Kotlin if I had more time. Let me know and I will tackle it!
