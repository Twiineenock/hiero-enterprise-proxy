# Hiero Enterprise Proxy

A Spring Boot REST proxy server that exposes the [hiero-enterprise-java](https://github.com/hiero-ledger/hiero-enterprise-java) SDK as a documented HTTP API, complete with Swagger UI. It allows enterprise clients to interact with the Hiero/Hedera network without a direct SDK dependency.

## Prerequisites

- Java 21
- Maven (or use the included `mvnw` wrapper)
- A funded Hedera testnet account ([portal.hedera.com](https://portal.hedera.com))

## Getting Started

### 1. Configure your environment

Create a `.env` file in the project root:

```env
HEDERA_ACCOUNT_ID=0.0.xxxxx
HEDERA_PRIVATE_KEY=your_operator_private_key_here
HEDERA_NETWORK=hedera-testnet
```

### 2. Run the server

**Linux / macOS:**
```bash
./mvnw spring-boot:run -pl hiero-proxy-server
```

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run -pl hiero-proxy-server
```

The server starts on `http://localhost:8080`.

### 3. Explore the API

Open Swagger UI in your browser:

```
http://localhost:8080/swagger-ui/index.html
```

## API Reference

### Accounts (`/api/v1/accounts`)

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/v1/accounts` | Create a new account (optional initial balance) |
| `GET` | `/api/v1/accounts/{accountId}/balance` | Get account HBAR balance |
| `GET` | `/api/v1/accounts/operator/balance` | Get operator HBAR balance |
| `GET` | `/api/v1/accounts/{accountId}/info` | Get detailed account info from mirror node |
| `PUT` | `/api/v1/accounts/{accountId}/key` | Rotate account key pair (server generates new key) |
| `PUT` | `/api/v1/accounts/{accountId}/memo` | Update account memo |
| `PUT` | `/api/v1/accounts/{accountId}` | Atomic key rotation and memo update |
| `POST` | `/api/v1/accounts/transfer` | Transfer HBAR from operator to an account |
| `POST` | `/api/v1/accounts/{accountId}/transfer` | Transfer HBAR between user accounts |
| `DELETE` | `/api/v1/accounts/{accountId}` | Delete account, balance transferred to operator |
| `DELETE` | `/api/v1/accounts/{accountId}/to/{recipientAccountId}` | Delete account, balance transferred to recipient |

### Topics (`/api/v1/topics`)

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/v1/topics` | Create a public topic |
| `POST` | `/api/v1/topics/private` | Create a private topic (server generates submit key) |
| `POST` | `/api/v1/topics/with-admin-key` | Create a public topic with a custom admin key |
| `POST` | `/api/v1/topics/private/with-admin-key` | Create a private topic with a custom admin key |
| `GET` | `/api/v1/topics/{topicId}` | Get topic info from mirror node |
| `GET` | `/api/v1/topics/{topicId}/messages` | Get all messages for a topic |
| `GET` | `/api/v1/topics/{topicId}/messages/{sequenceNumber}` | Get a specific message by sequence number |
| `PUT` | `/api/v1/topics/{topicId}/memo` | Update topic memo |
| `PUT` | `/api/v1/topics/{topicId}/admin-key` | Rotate topic admin key (server generates new key) |
| `PUT` | `/api/v1/topics/{topicId}/submit-key` | Rotate topic submit key (server generates new key) |
| `PUT` | `/api/v1/topics/{topicId}` | Atomic update of admin key, submit key, and memo |
| `POST` | `/api/v1/topics/{topicId}/messages` | Submit a text message to a topic |
| `POST` | `/api/v1/topics/{topicId}/messages/binary` | Submit a binary message (Base64-encoded) to a topic |
| `DELETE` | `/api/v1/topics/{topicId}` | Delete a topic |

## Key Design Decisions

- **Server-side key generation** — key rotation endpoints (`PUT .../key`, `PUT .../admin-key`, `PUT .../submit-key`) generate fresh ED25519 key pairs on the server. The caller provides only their current key to authorise the operation and receives the new key pair in the response. This is consistent with account creation where the server also generates the key pair.
- **Contextual success responses** — mutation endpoints that have no data to return respond with a `SuccessResponse` carrying a human-readable message rather than an empty `200` body.
- **Operator as default** — operations that require an admin or signing key default to the configured operator key when no custom key is provided.

## Dependencies

- [hiero-enterprise-spring](https://central.sonatype.com/artifact/org.hiero/hiero-enterprise-spring) `0.20.0` — auto-configures `AccountClient`, `TopicClient`, `AccountRepository`, `TopicRepository`, and all other Hiero service beans.
- [springdoc-openapi-starter-webmvc-ui](https://springdoc.org/) `2.5.0` — generates Swagger UI from the controller annotations.
