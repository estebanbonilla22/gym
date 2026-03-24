# Deploy Frontend (Angular) on AWS Amplify

This frontend lives in `frontend/` and connects to your deployed backend API.

## 1) Backend URL

Update API URL if needed:

- `src/environments/environment.ts`
- `src/environments/environment.prod.ts`

Set `apiBaseUrl` to your backend URL, for example:

`https://your-api-domain/`

## 2) Build locally (optional check)

```bash
npm install
npm run build
```

Build output will be in:

`dist/frontend/browser`

## 3) Deploy with AWS Amplify (recommended)

1. Go to **AWS Amplify** -> **Host web app**.
2. Choose **GitHub** and connect your repository:
   `estebanbonilla22/gym`
3. Branch: `main`
4. In **App root**, set:
   `frontend`
5. Use this build config:

```yaml
version: 1
frontend:
  phases:
    preBuild:
      commands:
        - npm ci
    build:
      commands:
        - npm run build
  artifacts:
    baseDirectory: dist/frontend/browser
    files:
      - '**/*'
  cache:
    paths:
      - node_modules/**/*
```

6. Save and deploy.

## 4) Redeploy after changes

Each `git push` to `main` triggers a new Amplify deploy automatically.
