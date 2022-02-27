# Memes
A website where you can authenticate yourself using Github then post memes. Memes

To even run this project, you will need to create an `.env` file at the project root before you can run `sudo docker compose -f docker_compose.yml up`.

## The `.env` file

Start your `.env` file with this blank template and start assigning each variable with its appropriate value:

```
GITHUB_OAUTH_ID=
GITHUB_OAUTH_SECRET=

POSTGRES_USER=
POSTGRES_PASSWORD=

SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/compose-postgres
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

- You will need to configure an [Oauth App on Github](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app) to get your client ID and client secret.
- Pick a username and password for locking the Postgres database.
