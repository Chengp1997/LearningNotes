# BookStore

**Vue.js+Typescript+Java**

This is a bookstore demo of class CS5244 Web Application. 

Demo can be viewed [here](http://cs5244.cs.vt.edu:8080/GepingBookstoreTransact/)

## Structure

- server: Backend 

  - Api: api& cors filter file
  - Business: codes about data model and database connection 

  <img src="images/image-20230104154132475.png" alt="image-20230104154132475" style="zoom:50%;" />

- client: front end

  - **Assets** - css/js resources
  - **Components** - self defined components 
  - **router**: path configuration
  - **Services**: ApiService, configure the path of the api from backend
  - **Store**: data accessing code. Accessing data from backend using store for better managing.
  - **Views**: pages that we access.
  - **App.vue**: main view of vue
  - **main.ts**: app configuration, start app
  - **vue.config.js**:  Path configuration of the whole frontend project.

  <img src="images/image-20230104154205106.png" alt="image-20230104154205106" style="zoom:50%;" />

## Technologies

| Technologies | Description         |
| ------------ | ------------------- |
| Vue.js       | Frontend tools      |
| Element UI   | Frontend framework  |
| JDBC         | database connection |
| MySQL        | Database            |
| Java         | Backend Language    |
| Pinia        | VUE store tools     |
| Typescript   | frontend            |
| Figma        | Design tool         |

## Demo

Homepage

<img src="images/image-20230104171612804-2870590.png" alt="image-20230104171612804" style="zoom:50%;" />

Category page

<img src="images/image-20230104171934704-2870778.png" alt="image-20230104171934704" style="zoom:50%;" />

Shopping cart

<img src="images/image-20230104174208004-2872132.png" alt="image-20230104174208004" style="zoom:50%;" />

Checkout Page

<img src="images/image-20230104174249408-2872172.png" alt="image-20230104174249408" style="zoom:50%;" />

Confirmation Page

<img src="images/image-20230104175228963-2872751.png" alt="image-20230104175228963" style="zoom:50%;" />

Empty cart

<img src="images/image-20230104180135834-2873299.png" alt="image-20230104180135834" style="zoom:50%;" />