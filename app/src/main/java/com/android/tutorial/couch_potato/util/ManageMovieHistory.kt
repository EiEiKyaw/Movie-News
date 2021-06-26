package com.android.tutorial.couch_potato.util

import com.android.tutorial.couch_potato.model.MovieHistory
import com.google.firebase.firestore.FirebaseFirestore

class ManageMovieHistory {
    companion object {
        fun manage(movie: MovieHistory, path: String) {
            val collection = FirebaseFirestore.getInstance().collection(path)
            val data: MutableMap<String, Any> = HashMap()
            data["imdbId"] = movie.imdbId
            if (path.contains("favorite"))
                data["isFavorite"] = movie.isFavorite
            else if (path.contains("bookmark"))
                data["isBookmark"] = movie.isBookmark
            var isValid = false
            var documentId = ""
            collection.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        if (document.data.getValue("imdbId").equals(movie.imdbId)) {
                            isValid = true
                            documentId = document.id
                        }
                    }
                    if (!isValid) {
                        collection.add(data)
                    } else {
                        if (path.contains("favorite"))
                            collection.document(documentId).update("isFavorite", movie.isFavorite)
                        else if (path.contains("bookmark"))
                            collection.document(documentId).update("isBookmark", movie.isBookmark)
                    }
                }
            }
        }
    }
}