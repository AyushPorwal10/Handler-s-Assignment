#  Recipe App

**Recipe App** is a modern Android application that helps users explore recipes from the Spoonacular API.  
Users can browse popular recipes, search for any dish, view full details (ingredients, instructions), and manage their own favourites list.  
Favourites are stored locally with Room, ensuring offline access for saved recipes.  

---

## Features

###  Home Screen
- Shows **Popular Recipes** (Random API) and **All Recipes (Search API)**  
- User-friendly recipe cards with images and titles  

###  Search Recipes
- Users can search for recipes by keywords (e.g., *Pizza*, *Pasta*)  
- Displays matching results in a clean list/grid  

###  Recipe Details
- Tapping any recipe opens a **Details screen** with:  
  - Ingredients  
  - Cooking instructions  
  - Image, title, servings, preparation time  

### ❤ Favourites
- Users can **favourite** or **unfavourite** any recipe  
- Favourites are **cached locally with Room DB**  
- Access favourites even offline  

---

## Screenshots
<table>
  <tr>
    <td>
      <img src="https://github.com/AyushPorwal10/README_SCREENSHOTS/blob/main/popular_all_recipes.png" alt="Home Screen" width="200"/>
      <h4>Home Screen</h4>
    </td>
    <td>
      <img src="https://github.com/AyushPorwal10/README_SCREENSHOTS/blob/main/search_recipes.png" alt="Search Screen" width="200"/>
      <h4>Search Recipes</h4>
    </td>
    <td>
      <img src="https://github.com/AyushPorwal10/README_SCREENSHOTS/blob/main/favourite_recipes.png" alt="Favourites" width="200"/>
      <h4>Favourites</h4>
    </td>
  </tr>
</table>

---

##  Tech Stack

- **Kotlin** – Primary language  
- **Jetpack Compose** – Modern declarative UI toolkit  
- **Hilt (Dagger-Hilt)** – Dependency injection  
- **Retrofit + OkHttp** – Networking (Spoonacular API)  
- **Room Database** – Local persistence for favourites  
- **Kotlin Coroutines & Flow** – For async tasks & data streams  
- **MVVM Architecture** – Clean separation of concerns  
- **Jetpack Navigation** – For seamless screen navigation  

---

##  APK
Drive Link : 

---

## 🔑 API Key Configuration

1. Get your **Spoonacular API key** from [Spoonacular API Console](https://spoonacular.com/food-api/console#Profile).  

2. Add the key inside **`AppModule`** of the `:network` module like this:  


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.spoonacular.com/"
    private const val API_KEY = "YOUR_API_KEY_HERE"
 // rest code
