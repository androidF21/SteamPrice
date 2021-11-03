Original App Design Project - README Template
===

# CounterPrice

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Steam Money is an Application that will allow users to check the price for the items in their inventory, look at other players users inventories, and search prices of different skins across the Steam Market.

### App Evaluation
- **Category:** Entertainment | Gaming
- **Mobile:** This app is being developed expressely for the Android ecosystem, though since it is being designed as a mobile app, it could be launched on iPhone at a later date. This app would also have usefulness as a desktop application.
- **Story:** Allows users to login and get their inventory and find the current price of the whole inventory and individual items. It would also allow people to look at other users inventories, and compare skins and inventory prices. This would also have a feed for the most looked up items, and a place search for you to look up a different item.
- **Market:** This would be for people who use steam, and play games where they collect skins that are able to sell on the Steam Market. Currently there are an estimated 120 Million montly users of Steam.
- **Habit:** People can use this app as much or as little as they would like, It would depend on how active they are in the Steam Trading market. 
- **Scope:** First the application will be developed with the different pages, and login. It will allow users to look up their items, add it to an inventory, and check prices based on their inventory. They will have a profile page, and a feed with items, and a search page for items and users on the app. When a user clicks on an item it brings up a detailed view of the item. There is opportinity for the scope to broaden as the project unfolds. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can login to the app 
* User can see the feed of items
* User can see their profile
* User can search for items
* When the User clicks on an item it pulls up a detailed view.  

**Optional Nice-to-have Stories**

* When you click on an item for the detail view the items in the collection come up
* User can see a list of top items
* Random Skin page

### 2. Screen Archetypes

* Login
   * When the user opens the app for the first time they are prompted to login.   This allows access to their profile. 
* Feed
   * This will have sections based on the different items in the market
   * This will allow for users to look through different items based on what they are looking for
   * When an item is clicked on it will bring it into a detailed page. 
* Item Detail Page
   * When a user clicks on an item it will launch a detailed view of the item, it will have the price of the item, the collection, and more detail about it other than the name and the price.
* Search Page
   * The search page will allow users to search for items and will bring up the item in the search results, to allow for easier access to the skins prices.
* Profile Page
   * The profile page will have the profile picture and the name of the current signed in user, as well as the items in their inventory.
   * It will have a list of their items as well as the individual prices of them, and a total price for all the items at the top. 

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Feed
* Search
* Profile

**Flow Navigation** (Screen to Screen)

* Login -> Registration 
* Login (If account) -> Feed
* Item in feed -> Detail Page
* Feed <-> Search <-> Profile (All pages can navigate to eachother with botton nav)

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://www.figma.com/proto/OWiF97lcB3pdI49SoBXHmX/SteamPrice-Design-Theory?node-id=2%3A8&scaling=scale-down&page-id=0%3A1&starting-point-node-id=2%3A8&show-proto-sidebar=1" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
