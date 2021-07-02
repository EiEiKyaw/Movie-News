package com.android.tutorial.couch_potato.util

import com.android.tutorial.couch_potato.model.MovieHistory
import com.google.firebase.firestore.FirebaseFirestore

class ManageMovieHistory {
    companion object {
        fun manage(movie: MovieHistory, path: String) {
            val collection = FirebaseFirestore.getInstance().collection(path)
            val data: MutableMap<String, Any> = HashMap()
            data[Constant.IMDB_ID] = movie.imdbId
            if (path == Constant.FAVORITE_PATH)
                data[Constant.IS_FAVORITE] = movie.isFavorite
            else if (path == Constant.BOOKMARK_PATH)
                data[Constant.IS_BOOKMARK] = movie.isBookmark
            var isValid = false
            var documentId = ""
            collection.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        if (document.data.getValue(Constant.IMDB_ID).equals(movie.imdbId)) {
                            isValid = true
                            documentId = document.id
                        }
                    }
                    if (!isValid) {
                        collection.add(data)
                    } else {
                        if (path == Constant.FAVORITE_PATH)
                            collection.document(documentId).update(Constant.IS_FAVORITE, movie.isFavorite)
                        else if (path == Constant.BOOKMARK_PATH)
                            collection.document(documentId).update(Constant.IS_BOOKMARK, movie.isBookmark)
                    }

//                    if (isValid) {
//                        collection.document(documentId).delete()
//                    } else {
//                        collection.add(data)
//                    }
                }
            }
        }
    }
}