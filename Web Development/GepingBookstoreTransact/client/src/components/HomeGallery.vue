<script setup lang="ts"></script>
<style scoped>
#gallery {
  padding: 45px 150px;
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}

* {
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
}

html,
body {
  height: 100%;
}

body {
  margin: 0;
  font: 16px/1.3 sans-serif;
}
/*
PURE RESPONSIVE CSS3 SLIDESHOW GALLERY by Roko C. buljan
http://stackoverflow.com/a/34696029/383904
*/

.CSSgal {
  position: relative;
  overflow: hidden;
  height: 100%; /* Or set a fixed height */
}

/* SLIDER */

.CSSgal .slider {
  height: 460px;
  white-space: nowrap;
  font-size: 0;
  transition: 0.8s;
}

/* SLIDES */

.CSSgal .slider > * {
  font-size: 1rem;
  display: inline-block;
  white-space: normal;
  vertical-align: top;
  height: 100%;
  width: 100%;
  /*background: none 50% no-repeat;*/
  background-size: cover;
}

/* PREV/NEXT, CONTAINERS & ANCHORS */

.CSSgal .prevNext {
  position: absolute;
  z-index: 1;
  top: 50%;
  width: 100%;
  height: 0;
}

.CSSgal .prevNext > div + div {
  visibility: hidden; /* Hide all but first P/N container */
}

.CSSgal .prevNext a {
  background: #fff;
  position: absolute;
  width: 60px;
  height: 60px;
  line-height: 60px; /* If you want to place numbers */
  text-align: center;
  opacity: 0.7;
  -webkit-transition: 0.3s;
  transition: 0.3s;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  left: 0;
}
.CSSgal .prevNext a:hover {
  opacity: 1;
}
.CSSgal .prevNext a + a {
  left: auto;
  right: 0;
}

/* NAVIGATION */

.CSSgal .bullets {
  position: absolute;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  width: 100%;
  text-align: center;
}
.CSSgal .bullets > a {
  display: inline-block;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-decoration: none;
  text-align: center;
  background: rgba(255, 255, 255, 1);
  -webkit-transition: 0.3s;
  transition: 0.3s;
}
.CSSgal .bullets > a + a {
  background: rgba(255, 255, 255, 0.5); /* Dim all but first */
}
.CSSgal .bullets > a:hover {
  background: rgba(255, 255, 255, 0.7) !important;
}

/* NAVIGATION BUTTONS */
/* ALL: */
.CSSgal > s:target ~ .bullets > * {
  background: rgba(255, 255, 255, 0.5);
}
/* ACTIVE */
#s1:target ~ .bullets > *:nth-child(1) {
  background: rgba(255, 255, 255, 1);
}
#s2:target ~ .bullets > *:nth-child(2) {
  background: rgba(255, 255, 255, 1);
}
#s3:target ~ .bullets > *:nth-child(3) {
  background: rgba(255, 255, 255, 1);
}
#s4:target ~ .bullets > *:nth-child(4) {
  background: rgba(255, 255, 255, 1);
}
/* More slides? Add here more rules */

/* PREV/NEXT CONTAINERS VISIBILITY */
/* ALL: */
.CSSgal > s:target ~ .prevNext > * {
  visibility: hidden;
}
/* ACTIVE: */
#s1:target ~ .prevNext > *:nth-child(1) {
  visibility: visible;
}
#s2:target ~ .prevNext > *:nth-child(2) {
  visibility: visible;
}
#s3:target ~ .prevNext > *:nth-child(3) {
  visibility: visible;
}
#s4:target ~ .prevNext > *:nth-child(4) {
  visibility: visible;
}
/* More slides? Add here more rules */

/* SLIDER ANIMATION POSITIONS */

#s1:target ~ .slider {
  transform: translateX(0%);
  -webkit-transform: translateX(0%);
}
#s2:target ~ .slider {
  transform: translateX(-100%);
  -webkit-transform: translateX(-100%);
}
#s3:target ~ .slider {
  transform: translateX(-200%);
  -webkit-transform: translateX(-200%);
}
#s4:target ~ .slider {
  transform: translateX(-300%);
  -webkit-transform: translateX(-300%);
}
/* More slides? Add here more rules */

/* YOU'RE THE DESIGNER!
   ____________________
   All above was mainly to get it working :)
   CSSgal CUSTOM STYLES / OVERRIDES HERE: */

.CSSgal {
  color: #fff;
  text-align: center;
}
.CSSgal .slider h2 {
  margin-top: 40vh;
  font-weight: 200;
  letter-spacing: -0.06em;
  word-spacing: 0.2em;
  font-size: 3em;
}
.CSSgal a {
  border-radius: 50%;
  margin: 0 3px;
  color: rgba(0, 0, 0, 0.8);
  text-decoration: none;
}
.background-1 {
  background-image: url("@/assets/images/icon/spy-x-family.png");
}
.background-2 {
  background-image: url("@/assets/images/icon/harry-potter.png");
}
.background-3 {
  background-image: url("@/assets/images/icon/dune.png");
}
.background-4 {
  background-image: url("@/assets/images/icon/jurassic-park.png");
}
</style>

<template>
  <div id="gallery" class="center">
    <div class="CSSgal">
      <!-- Don't wrap targets in parent -->
      <s id="s1"></s>
      <s id="s2"></s>
      <s id="s3"></s>
      <s id="s4"></s>

      <div class="slider">
        <div class="background-1">
          <h2>Spy-X-Family:By Tatsuya Endo</h2>
          <p>Best Comics this week</p>
        </div>
        <div class="background-2">
          <h2>Harry Potter:By J.K Rowling</h2>
          <p>Best Adventure this week</p>
        </div>
        <div class="background-3">
          <h2>Dune:By Frank Herbert</h2>
          <p>Best Sci-Fi this week</p>
        </div>
        <div class="background-4">
          <h2>The Illiad:By Homer</h2>
          <p>Best Classic this week</p>
        </div>
      </div>

      <div class="prevNext">
        <div><a href="#s4">&lt;</a><a href="#s2">&gt;</a></div>
        <div><a href="#s1">&lt;</a><a href="#s3">&gt;</a></div>
        <div><a href="#s2">&lt;</a><a href="#s4">&gt;</a></div>
        <div><a href="#s3">&lt;</a><a href="#s1">&gt;</a></div>
      </div>

      <div class="bullets">
        <a href="#s1">1</a>
        <a href="#s2">2</a>
        <a href="#s3">3</a>
        <a href="#s4">4</a>
      </div>
    </div>
  </div>
</template>
