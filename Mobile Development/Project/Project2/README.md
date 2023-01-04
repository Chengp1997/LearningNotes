# Project2 - DreamCatcher

Project2 of CS5244 Mobile Development



A information displaying app using database.

Activity and fragment are used in this app. One activity will hold multiple fragments.

- In DreamListFragment, app will **show** all the dreams of the list getting from the database
- In DreamListFragment, dream can be **deleted** if swipe the dream to the left.
- In DreamListFragment, new dream can be **added**.
- Users can click on the dream to see the detail information of the dream.
- In DreamDetailFragment, dream can be deffered or fulfilled
  - if fulfilled, no new reflections can be added.
  - if deffered, a deffered sign will be showed.
- In DreamDetailFragment, details can be **shared**. Users can also **taken picture** of their dreams.

## Environment

**Version control tool: ** Gradle

**Language**: Kotlin

**IDE**: Android Studio

**Test Tool**: espresso

**Database Framework**: Room



## Layouts

DreamCatcherList.png

<img src="/Users/chengeping/Documents/LearningMaterial/LearningNotes/Mobile Development/Project/Project2/IMG/DreamCatcherList.png" alt="image-20230103175447292" style="zoom:50%;" />

DreamCatcherDetail

<img src="/Users/chengeping/Documents/LearningMaterial/LearningNotes/Mobile Development/Project/Project2/IMG/DreamCatcherDetail.png" alt="image-20230103175609172" style="zoom:50%;" />

Share Detail Dream

<img src="/Users/chengeping/Documents/LearningMaterial/LearningNotes/Mobile Development/Project/Project2/IMG/DreamCatcherShare.png" alt="image-20230103175650374" style="zoom:50%;" />

Taking Photos

<img src="/Users/chengeping/Documents/LearningMaterial/LearningNotes/Mobile Development/Project/Project2/IMG/DreamCatcherPhotos.png" alt="image-20230103175742975" style="zoom:50%;" />



## Technologies used

In this app. There are only one Activity.

**Activity will hold two fragments.**

Viewmodel is used to pass data between fragments.

Viewmodel lives as long as the fragment lifecycle.

view binding happened in onCreatView stage. 

Data loading happened in onViewCreated stage.

**RecyclerView** is used in this app. Some fragments can be used multiple times. 







