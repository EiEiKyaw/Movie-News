package com.android.tutorial.couch_potato.model

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Released") val releasedYear: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val category: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val posterImg: String,
    @SerializedName("Ratings") val ratings: List<Rating>,
    @SerializedName("Metascore") val metascore: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbVotes") val imdbVotes: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Production") val production: String,
    @SerializedName("Website") val website: String
) {
}

//http://www.omdbapi.com/?apikey=9494436b&t=batman&plot=full
//{
//"Title": "Batman",
//"Year": "1989",
//"Rated": "PG-13",
//"Released": "23 Jun 1989",
//"Runtime": "126 min",
//"Genre": "Action, Adventure",
//"Director": "Tim Burton",
//"Writer": "Bob Kane (Batman characters), Sam Hamm (story), Sam Hamm (screenplay), Warren Skaaren (screenplay)",
//"Actors": "Michael Keaton, Jack Nicholson, Kim Basinger, Robert Wuhl",
//"Plot": "Gotham City. Crime boss Carl Grissom (Jack Palance) effectively runs the town but there's a new crime fighter in town - Batman (Michael Keaton). Grissom's right-hand man is Jack Napier (Jack Nicholson), a brutal man who is not entirely sane... After falling out between the two Grissom has Napier set up with the Police and Napier falls to his apparent death in a vat of chemicals. However, he soon reappears as The Joker and starts a reign of terror in Gotham City. Meanwhile, reporter Vicki Vale (Kim Basinger) is in the city to do an article on Batman. She soon starts a relationship with Batman's everyday persona, billionaire Bruce Wayne.",
//"Language": "English, French, Spanish",
//"Country": "USA, UK",
//"Awards": "Won 1 Oscar. Another 8 wins & 26 nominations.",
//"Poster": "https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg",
//"Ratings": [
//{
//"Source": "Internet Movie Database",
//"Value": "7.5/10"
//},
//{
//"Source": "Rotten Tomatoes",
//"Value": "71%"
//},
//{
//"Source": "Metacritic",
//"Value": "69/100"
//}
//],
//"Metascore": "69",
//"imdbRating": "7.5",
//"imdbVotes": "343,300",
//"imdbID": "tt0096895",
//"Type": "movie",
//"DVD": "24 Jul 2014",
//"BoxOffice": "$251,348,343",
//"Production": "Warner Brothers, Guber-Peters Company, PolyGram Filmed Entertainment",
//"Website": "N/A",
//"Response": "True"
//}