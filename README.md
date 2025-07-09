# BeanPack-Java

**BeanPack-Java** is the official Java SDK for the **BeanChain** blockchain network. It provides the foundational models, cryptographic utilities, wallet structures, and reusable components shared across all Java-based BeanChain projects — including validator nodes, contract execution nodes, reward engines, and developer tooling.

> **Current Version:** `0.1.2`  
> **Compatible with:** BeanNode v0.1.0+  
> Java is the first supported language — Go, Python, and JavaScript SDKs are planned and in active development. Want to contribute? [Reach out to the team](#contact).

---

## Overview

**BeanPack-Java** is designed for modular integration with:

- **BeanNode** — Core validator node  
- **CEN** — Contract Execution Node  
- **RN** — Reward Node  
- **Peripheral tools** — Wallets, indexers, sidechains, and custom modular nodes

---

## Included Modules

### `beanify`
- `Branding.java` — Constants and text assets for branding
- `Color.java` — BeanChain color presets

### `block`
- `Block.java` — Block model and Merkle root generation
- `BlockHeader.java` — Header and metadata structure

### `CENdev`
- `BaseContract.java` — Base contract structure
- `CallManager.java` — CENCALL execution manager *(WIP)*

### `crypto`
- `SHA256TransactionSigner.java` — ECDSA signing (secp256k1)
- `TransactionVerifier.java` — Signature and transaction validation
- `WalletGenerator.java` — Keypair generation, address derivation

### `logger`
- `PackLoggerManager.java` — Internal logger forked from `beanLogger`

### `models`
- `Layer2Wallet.java` — L2 token wallet
- `StateWallet.java` — L1 wallet (nonce + balance)
- `TokenStorage.java` — Internal mapping of token state

### `rejection`
- `Flagger.java` — TX rejection flag helper

### `security`
- `SecuritySetup.java` — Bouncy Castle initializer

### `txs` (Transaction Types)
- `TX.java` — Base transaction class
- `MintTX.java` — L2 token minting
- `TokenTX.java` — L2 token transfer
- `TokenCENTX.java` — Contract-issued token TX
- `StakeTX.java` — Internal staking transaction
- `AirdropTX.java` — Reward Node airdrop or faucet TX
- `FundedCallTX.java` — A special transaction that, once validated, triggers the system to wrap itself inside a `CENCALL` targeting the contract it is funding. This enables funded contract calls to execute with on-chain approval.

### `utils`
- `AddressUtils.java` — Address format tools
- `beantoshinomics.java` — BEAN ↔ beantoshi conversion
- `hex.java` — Hex encode/decode helpers
- `MerkleHelper.java` — Merkle root calculation utilities
- `ParamBuilder.java` — Param assembly tool
- `TXSorter.java` — TX ordering and grouping logic

### `wizard`
- `secure.java` — Layer 1 wizard encryption tools
- `wizard.java` — Encryption core (WizKey handler)
- `WizardGlob.java` — Glob for storing encrypted keys for use in the CEN *(WIP- Needs to be updated to L2 `WizCrypt`, and adapted as we build the `CEN`)*
- `WizCryptHandler.java` — Layer 2 WizKey encryption and config file encryption handler using `WizCrypt`

### `head`
- `headjava` — Version variable

### Other
- `CENCALL.java` — Special class representing off-chain contract calls; not a subclass of TX but used to structure external logic. Acts as a root structure for outbound contract flows and may expand independently.

---

## Usage

BeanPack-Java is available via GitHub Packages. To include it in a Maven project:

### Step 1: Add GitHub Packages repository

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/BeanChain-Core/BeanPack-Java</url>
  </repository>
</repositories>
```

### Step 2: Add the dependency

```xml
<dependency>
  <groupId>com.beanchain</groupId>
  <artifactId>bean-pack</artifactId>
  <version>0.1.2</version>
</dependency>
```

> See [Releases](https://github.com/BeanChain-Core/BeanPack-Java/releases) for changelogs and updates.

### Step 3: GitHub Credentials

In your `~/.m2/settings.xml`:

```xml
<servers>
  <server>
    <id>github</id>
    <username>YourGitHubUsername</username>
    <password>ghp_your_personal_access_token</password>
  </server>
</servers>
```

> Token must include `read:packages` and `repo` scopes.

---

## SDK Roadmap

Additional language SDKs are underway:

- **BeanPack-Go** — Lightweight SDK for embedded tooling
- **BeanPack-Py** — (Planned) Python support for dApps and utilities
- **BeanPack-JS** — (Planned) JavaScript SDK for frontends and web tooling

---

## License

MIT License — see [`LICENSE`](LICENSE)

---

## Contact

**Email:** [team@limabean.xyz](mailto:team@limabean.xyz)  
**GitHub:** [BeanChain-Core](https://github.com/BeanChain-Core)

Crafted by the **BeanChain Core Team**  
Stewarded by **Outlandish Tech**, powered by **Outlandish Creative LLC**
