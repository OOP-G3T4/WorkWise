<a id="readme-top"></a>

---
## Table of Contents
1. [About The Project](#about-the-project)  
   - [Built With](#built-with)  
   - [Team Members](#team-members)  
2. [Prerequisites](#prerequisites)  
3. [Installation](#installation)  
   - [Clone](#clone)  
   - [Frontend Setup](#frontend-setup)  
   - [Backend Setup](#backend-setup)  
4. [Test Accounts](#test-accounts) 

---
## About The Project

<p align="center">
    <img src="frontend/public/workwise_main.svg" >
</p>

<p align=center>
  WorkWise is a smart, AI-powered scheduling app for P&C Maintenance Services. It automates task assignments based on worker availability, proximity, and client preferences, with real-time notifications, streamlined shift management, and performance tracking for efficient daily operations.
</p>


<br/>

### Built With

* <a href=""><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" width="26px"></a>
* <a href=""><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="26px"></a>
* <a href=""><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vitejs/vitejs-original.svg" width="26px"></a>
* <a href="https://html.com/"><img width="26px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg"/></a>
* <a href="https://developer.mozilla.org/en-US/docs/Web/CSS"><img width="26px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg"/></a>
* <a href="https://www.javascript.com/"><img width="26px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg"/></a>
* <a href="https://vuejs.org/"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg" width="26px"></a>
* <a href="https://getbootstrap.com"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original.svg" width="26px"></a>

<br/>

### Team Members

| Members               | School Email |
| --------------------- | ---------------- |
| Tan Sheng Da | shengda.tan.2021@scis.smu.edu.sg |
| Glen Low Jun Jie | glen.low.2021@scis.smu.edu.sg |
| Seth Yap Ziqi | sethyap.2021@scis.smu.edu.sg |
| Adam Bin Firdaus Tan | adamft.2021@scis.smu.edu.sg |
| Darryl Soh Soon Yong | darryl.soh.2021@scis.smu.edu.sg |
| Poo Jun Sui | junsui.poo.2021@scis.smu.edu.sg |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br/> 

---

## Prerequisites
Before running the application, ensure your system meets the following requirements:  

- **WAMP (for Windows)** or **MAMP (for Mac)**: Required to set up a MySQL database.  
  - [Download WAMP](https://www.wampserver.com/en/)  
  - [Download MAMP](https://www.mamp.info/en/mac/)  

- **Node.js**: Required for the frontend. [Download Node.js](https://nodejs.org/).  

- **Java JDK 17 or higher**: Required for the backend. [Download Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html).  

<p align="right">(<a href="#readme-top">back to top</a>)</p>  

---

## Installation 
### Clone
Clone the github repository to your local machine
```bash
git clone https://github.com/OOP-G3T4/WorkWise.git
```

### Frontend Setup
1. Navigate to the `frontend` directory 
  ```bash 
  cd frontend
  ```
2. Install dependencies 
  ```bash 
  npm install
  ```
3. Run the application 
  ```bash
  npm run dev
  ```
4. If you would like to set up WhatsApp Service, create an `.env` file in the `frontend` directory. Note: you need a Twilio account.
  ```bash
  VITE_TWILIO_ACCOUNT_SID=your-twilio-account-sid
  VITE_TWILIO_AUTH_TOKEN=your-twilio-auth-token
  VITE_TWILIO_FROM_NUMBER=your-twilio-from-num
  VITE_TWILIO_TO_NUMBER=your-twilio-to-num
  ```


### Backend Setup 
1. Create an `.env` file in the backend directory.
2. Add the following environment variables:
  ```bash
  MYSQL_URL=jdbc:mysql://localhost:3306/workwise  
  MYSQL_USERNAME=your-mysql-username  
  MYSQL_PASSWORD=your-mysql-password  
  OPENAI_API_KEY=your-api-key  
  ```
3. Initialise the application 
  ```bash
  mvn clean install
  ```
4. Run the backend 
  ```bash
  mvn spring-boot:run
  ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>  

### Test Accounts 

#### Admin

| **Email**       | bossyface@example.com|
| -------------- | ------------------|
| **Password**       | password321  


#### Employee

| **Email**       | marky.mcfly@example.com|
| -------------- | ------------------|
| **Password**       | password123  


<p align="right">(<a href="#readme-top">back to top</a>)</p>