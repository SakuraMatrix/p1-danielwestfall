# Shopping eWallet Manager

Everyday, online shopping applications fail to handle money transfers in a timely manner. With requests to fill up eWallets failing and users missing out on sales.
The goal of this application will be to manage an eWallet effectively. Making it capable of handling dozens of orders very quickly without failing transfers.

## Goals
- eWallet that is capable of being added to and used for purchases.
- Transfers from real bank to online bank made quick and easy.
- Transfers are secure and help block potentially malicious or unintended transfers.
- Checkout purchases made quick, simple and reliable.
- All incoming requests should be handled without rolling failures or significant delays.
- Users should not have to make multiple failed requests to add money to wallet without getting funds.
## User Stories
- User requests to add money to eWallet from their bank.
- User requests to cancel money transfer.
- Block malicious user from transferring money from fake bank.
- Allow users to spend eWallet money in online store.
- Allow users to cancel order within a day of purchase.
## REST Queries
- "/user" to access site to create a user
- "/user/user_id" to access user info
- "/transfer" to setup a transfer
- "/transfer/transfer_id" to access transfer info
- "/pay" to setup a withdrawal