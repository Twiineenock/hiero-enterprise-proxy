# Hiero Enterprise Proxy

A production-ready REST gateway to the [Hiero](https://hiero.org) distributed ledger network. It wraps the [hiero-enterprise-java](https://github.com/hiero-hackers/hiero-enterprise-java) SDK and exposes everything as a standard HTTP API — so you can build on Hiero from **any language**, without touching Java or the SDK.

## Why this exists

The Hiero SDK is Java-first. If you're building in Python, JavaScript, Go, or anything else, you previously had no clean way to interact with the network. This proxy solves that: run one container, get a fully documented REST API, and start making HTTP requests.

## Quickstart

You need a funded [Hedera testnet account](https://portal.hedera.com) and Docker.

**Linux / macOS**
```bash
docker run \
  -e HEDERA_ACCOUNT_ID=0.0.xxxxx \
  -e HEDERA_PRIVATE_KEY=your_private_key \
  -e HEDERA_NETWORK=hedera-testnet \
  -p 8080:8080 \
  ghcr.io/hiero-hackers/hiero-enterprise-proxy:latest
```

**Windows (PowerShell)** — use backticks instead of backslashes for line continuation:
```powershell
docker run `
  -e HEDERA_ACCOUNT_ID=0.0.xxxxx `
  -e HEDERA_PRIVATE_KEY=your_private_key `
  -e HEDERA_NETWORK=hedera-testnet `
  -p 8080:8080 `
  ghcr.io/hiero-hackers/hiero-enterprise-proxy:latest
```

Open `http://localhost:8080` — you'll find the landing page, interactive API explorer, and OpenAPI spec.

## Running locally with Docker Compose

This is the easiest approach on any OS — no line-continuation differences to worry about.

```bash
# 1. Clone the repo
git clone https://github.com/hiero-hackers/hiero-enterprise-proxy.git
cd hiero-enterprise-proxy

# 2. Create your .env file and add your Hedera credentials
cp .env.example .env

# 3. Start the container
docker compose up
```

The `.env` file should contain:
```
HEDERA_ACCOUNT_ID=0.0.xxxxx
HEDERA_PRIVATE_KEY=your_private_key
HEDERA_NETWORK=hedera-testnet
```

## Running without Docker (Java 21 required)

**Linux / macOS**
```bash
./mvnw spring-boot:run -pl hiero-proxy-server
```

**Windows (PowerShell)**
```powershell
.\mvnw.cmd spring-boot:run -pl hiero-proxy-server
```

## Configuration

All configuration is done through environment variables (or a `.env` file in the project root):

| Variable | Required | Description |
|---|---|---|
| `HEDERA_ACCOUNT_ID` | ✅ | Your operator account (e.g. `0.0.12345`) |
| `HEDERA_PRIVATE_KEY` | ✅ | Your operator private key (DER-encoded hex) |
| `HEDERA_NETWORK` | — | `hedera-testnet` (default), `hedera-mainnet`, `hedera-previewnet` |
| `SERVER_PORT` | — | HTTP port (default `8080`) |

## API coverage

The full interactive spec lives at `/swagger-ui/index.html`. The proxy covers:

- **Accounts** — create, fund, transfer HBAR, rotate keys, query balance and info
- **Fungible Tokens (HTS)** — create, mint, burn, transfer, associate/dissociate
- **NFTs (HTS)** — create token type, mint, burn, transfer, query
- **Topics (HCS)** — create public/private topics, submit and query messages
- **Smart Contracts** — deploy and call contracts
- **Files** — create, read, query size
- **Network** — query network info and fee schedules
- **Blocks & Transactions** — query by number or ID

## How it works

```
Your app  →  HTTP/JSON  →  Hiero Enterprise Proxy  →  gRPC/HTTPS  →  Hiero Network
```

The proxy is a thin translation layer. It accepts HTTP requests, delegates to the `hiero-enterprise-spring` client beans, and converts the result — or any exception — into a consistent JSON response. There is no database, no cache, and no business logic here.

**Key behaviour to know:**

- Key generation happens server-side. When you create an account or rotate a key, the proxy generates a fresh ED25519 key pair and returns the private key once in the response. Save it — it won't be shown again.
- All errors follow the same shape: `{ "status": <http-code>, "message": "<reason>" }`.
- The operator account (from your env) pays all transaction fees.

## CI/CD

Every push to `main` builds and publishes a multi-platform image (`linux/amd64` + `linux/arm64`) to:

```
ghcr.io/hiero-hackers/hiero-enterprise-proxy:latest
```

Versioned releases are tagged from semver git tags (`v1.2.3` → `:v1.2.3`, `:1.2`, `:1`).

## Contributing

Contributions are welcome. Please sign your commits (`git commit -s`) — the repo enforces DCO.

Built on [`org.hiero:hiero-enterprise-spring:0.20.0`](https://central.sonatype.com/artifact/org.hiero/hiero-enterprise-spring) from Maven Central.
