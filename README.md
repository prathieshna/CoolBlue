# CoolBlue Android Assignment 
The purpose of the assignment was to create an Android application that would allow the user to search for products and scroll through them so that they can find something interesting to buy.

# Architecture
Redux is an architecture in which all of the app's state lives in one container. The only way to change state is to create a new state based on the current state and a requested change. The Store holds all of the app's state. An Action is immutable data that describes a state change.

Redux was inspired by Flux. It has studied the Flux architecture and removed unnecessary complexity. The major differences:
- Redux does not have a Dispatcher concept.
- Redux has only one Store whereas Flux has many Stores.
- The Action objects will be received and handled directly by Store.

<img src="/docs/Architecture.png" height="400" />

# UI/UX Inspiration
The current implementation is an inspiration from the exsisting application. Following are the side by side comparison of the exsisting application and the current implementation. Both Dark mode and light mode are implemented.

<p>
  <img src="/docs/Exsisting - Dark.jpg" width="200" />
  <img src="/docs/New - Dark.jpg" width="200" />
  <img src="/docs/Exsisting - Light.jpg" width="200" />
  <img src="/docs/New - Light.jpg" width="200" />
</p>

# Features
Attempted to implement the following
- Infinite scrolling, It is a web-design technique that loads content continuously as the user scrolls down the page, eliminating the need for pagination. Due to the mock API limitations it doesn't scroll due to page count and current page size being the same.
- Unique Selling Points in Bulleted list using Bullet Span
- Custom SVG rating bar which allows high res SVG imagaes to be used instead of PNG - Thanks to the Author of https://github.com/Malligan/RatingBarSvg 
- Custom Design elements in the UI used to Highlight CoolBlue Choices
- Discounted Amount Display
