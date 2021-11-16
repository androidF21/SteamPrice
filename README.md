Original App Design Project - README Template
===

# SteamPrice

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
Direct link: https://www.figma.com/file/OWiF97lcB3pdI49SoBXHmX/SteamPrice-Design-Theory?node-id=0%3A1
<img src="https://github.com/androidF21/SteamPrice/blob/main/digitalwireframe.png" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/androidF21/SteamPrice/blob/main/steampricewalkthrough.gif" width=600>
### [BONUS] Interactive Prototype

https://www.figma.com/proto/OWiF97lcB3pdI49SoBXHmX/SteamPrice-Design-Theory?node-id=2%3A8&scaling=scale-down&page-id=0%3A1&starting-point-node-id=2%3A8&show-proto-sidebar=1

## Schema 
### Models
User

| Property  | Type | Descripion |
| ------------- | ------------- | ------------- |
| name  | String  | name of the user logged in |
| inventory value  | Number  | total value of all items in inventory |
| inventory items  | String  | list of all items in inventory on last refresh |
| profile picture  | File  | profile picture of the user  |

Item

| Property  | Type | Descripion |
| ------------- | ------------- | ------------- |
| name  | String  | name of the item |
| price  | Number  | current market price of the item |
| item collection  | String | collection the item belongs to or collection of the case |
| item wear  | String | wear value of the item if it is a weapon skin  |
| item picture  | File  | picture of the item  |
| item type  | String  | case/capsule or weapon skin  |

### Networking
* Login Page
   * (Read/GET) Query logged in user object
* Feed Page
   * (Read/GET) Query top 20 items currently sold on the cs:go steam community market
* Item Detail Page
   * (Read/GET) Query specific item and details of the item that the user clicked on
* Search Page
   * (Read/GET) Query items matching the keyword the user searched for
* Profile Page
   * (Read/GET) Query items in user's inventory through steam api 
   * (Read/GET) Get total inventory price from backpack.tf api
   * (Update/PUT) Update user's stored inventory if inventory is refreshed
