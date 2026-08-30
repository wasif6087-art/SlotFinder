
  Remember before deploying -

 
  3. Maybe improve data logging? (Do this during reliability/Testing)

  
  ---

  Question: 
  

  Housekeeping questions:

  First, we do not need your existing local database data. I would start production with a clean database. All those old test watch requests, fake emails, old notification records, etc. are development artifacts. Migrating them with pg_dump would add work with essentially no benefit. Hibernate can create a fresh schema automatically because you currently have ddl-auto=update.

Second, your Gmail app password being committed to application.properties is genuinely worth fixing before we push further into deployment. Not because it blocks RDS, but because once we start treating this repository as a deployable/resume project, secrets should not live in source control. We can handle that alongside the database credentials when we convert application.properties to environment variables.

3) Currently application properties contains master username and password. Do we need to do anything about this?
  
  