# Phone contacts

## Description

A small REST API that includes contacts management, such as basic CRUD, import/export to/from Excel, upload an image, view images of contacts and more

Application:
* Java 17
* Maven
* Postgres

### Setting up a project:

#### 1) Clone Repository

   ```
   git clone https://github.com/cumbainer/phone-contacts.git  
   cd phone-contacts
   ```

#### 2) Start project

  ```
  mvn clean install
  java <env variables: -DvariableName='value'> -jar <jar location>
  ```

## ENVIRONMENT VARIABLES

* DB_URI(required) - your URI for DB connection
* DB_USERNAME - DB username to access local DB
* DB_PASSWORD - DB password to access local DB
* PV_API_KEY - Your api key to validate phone numbers(https://numverify.com/)
* EV_API_KEY - Your api key for API to validate emails(https://app.abstractapi.com/)


### API ENDPOINTS
Base-url: `http:localhost:3030/api/v1`
* `POST /accounts/new` Create an account to further do auth with received data(username, password)
* `POST /contacts/new` Create a new contact. Name is only required param
* `PUT /contacts/{contactId}/update` Update existing contact. ContactId is only required param
* `DELETE /contacts/{contactId}/delete` Delete existing contact. ContactId is only required param
* `GET /contacts/{contactId}/get` Get 1 contact. ContactId is only required param
* `GET /contacts/get` Get all contacts.
* `GET /contacts/export` Export all contacts in Excel file
* `POST /contacts/import` Import all contacts from Excel file. Excel file must include headers at first row.
* `GET /contacts/{contactId}/image` Get image of specific contact. ContactId is only required param.