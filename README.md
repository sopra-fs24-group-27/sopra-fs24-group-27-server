# SoPra FS24 Group 27 Project - LyricLies🎵

## Authors
- Yating Pan - [GitHub](https://github.com/YatingPan)
- Hepeng Fan - [GitHub](https://github.com/bazziprincess)
- Qingcheng Wang - [GitHub](https://github.com/wqc260615)
- Yuying Zhong - [GitHub](https://github.com/Yuying8777)
- Yi Zhang - [GitHub](https://github.com/imyizhang)

## Project Description
This group project from Group 27 in the Software Engineering Lab FS24 at UZH aims to develop "LyricLies," a multiplayer online game that combines music and emojis. Players use their knowledge of music and non-literal expression skills to identify the "lyric spy" among them. This entertaining social platform promotes the exploration of musical perception and non-verbal communication, thereby enriching the social gaming landscape with educational and fun elements.

## LyricLies🎵

The objective of LyricLies is to identify the spy who listens to a different song, using emojis to describe their song. The game supports 4 players and utilizes songs from the [Spotify API](https://developer.spotify.com/documentation/web-api) to assign identities to each player. Suitable for audiences aged 10 and up, the game begins with each player assigned a song, but only one player receives a different track. Throughout two rounds of emoji descriptions, players must then vote to guess the spy. If the spy is correctly identified, the non-spy players win; otherwise, the spy claims victory.

### Game Setup
After registration and login, players can create or join a game room. The game starts once four players are present, initiated by the room owner. Privacy during gameplay is crucial to ensure fair play.

### Identity Assignment
The system randomly selects songs through the Spotify API, assigning the same song to three players and a different one to the spy. All players listen to their song for 30 seconds simultaneously.

### Emoji Description
Post-listening, players have 60 seconds to select up to five emojis to describe their song, taking turns. The choice of emojis can reflect the song's mood, style, lyrics, or vibe. Players must be strategic in their descriptions, as the spy's identity is unknown.

### Guessing and Voting
After two rounds of description, a two-minute discussion phase allows players to speculate on the spy's identity, followed by a voting phase.

### End of Game
The game concludes by revealing the true identities and songs, determining the winning side based on the votes. Players' scores are updated accordingly, rewarding strategic deception and deduction.

Embark on a journey of musical espionage - can you outsmart your friends and win the game of LyricLies? 🤔

```
sopra-fs24-template-server
├─ .editorconfig
├─ .git
│  ├─ .COMMIT_EDITMSG.swp
│  ├─ COMMIT_EDITMSG
│  ├─ config
│  ├─ description
│  ├─ FETCH_HEAD
│  ├─ HEAD
│  ├─ hooks
│  │  ├─ applypatch-msg.sample
│  │  ├─ commit-msg.sample
│  │  ├─ fsmonitor-watchman.sample
│  │  ├─ post-update.sample
│  │  ├─ pre-applypatch.sample
│  │  ├─ pre-commit.sample
│  │  ├─ pre-merge-commit.sample
│  │  ├─ pre-push.sample
│  │  ├─ pre-rebase.sample
│  │  ├─ pre-receive.sample
│  │  ├─ prepare-commit-msg.sample
│  │  ├─ push-to-checkout.sample
│  │  └─ update.sample
│  ├─ index
│  ├─ info
│  │  └─ exclude
│  ├─ logs
│  │  ├─ HEAD
│  │  └─ refs
│  │     ├─ heads
│  │     │  ├─ development
│  │     │  └─ main
│  │     └─ remotes
│  │        └─ origin
│  │           ├─ development
│  │           └─ main
│  ├─ objects
│  │  ├─ 01
│  │  │  ├─ 17b0ecce6451588c5fd27a95badc7bb48c693f
│  │  │  ├─ 1bbd618a1438dd2cf54d34615ed471fae4df13
│  │  │  ├─ 41dd34153909b54a4e7f32b52acac73941fcab
│  │  │  └─ c9b284e74a837cf9928a8c74e13d1f08e13ee9
│  │  ├─ 02
│  │  │  ├─ 44492f148d790b2a22ab63d89d9602af563d20
│  │  │  └─ d944267b80ee65666c1967d8448e8961967caf
│  │  ├─ 03
│  │  │  └─ 25e1709297fd58f008f7f664a459004978c9f6
│  │  ├─ 04
│  │  │  ├─ 117786458f9f0d7abfff9ef6220a196a9c58b6
│  │  │  ├─ 4f02586c1b6325235d8a166b4d7c4bd636388a
│  │  │  ├─ bfb97118531c60656b6c3743bcd42046d75751
│  │  │  └─ d7509173e3963a003a6ddc106d1c9b59468ecb
│  │  ├─ 05
│  │  │  └─ e86d1830e42987329bca0ff55063f1c505c4d9
│  │  ├─ 06
│  │  │  ├─ 6a89e8aba4ab85426ebfc25bd71dc73e1c3411
│  │  │  └─ a758698dfdbb3d9ac301127c15a5730a78979a
│  │  ├─ 07
│  │  │  ├─ d6d0efb02174cea184542a6b74d61002214525
│  │  │  └─ e2ba4a16ce85c900f0e215dd06f7c0037abfc4
│  │  ├─ 09
│  │  │  ├─ 8260b43f4bb325b919ef2722db01f7c46f8f9c
│  │  │  └─ ede0b66cb1bd2d49dcac12388a00dd7463cdec
│  │  ├─ 0a
│  │  │  └─ 81683594ab073fc178f49686a6cefab7bdec28
│  │  ├─ 0b
│  │  │  └─ 6181a47928b0f170971b93f68dc3d0085dacdf
│  │  ├─ 0c
│  │  │  ├─ 29aca6747112c3b6406b23558cc545e7a8808f
│  │  │  └─ ef36e0c84745801fe7416e3e06eb8db32395dd
│  │  ├─ 0d
│  │  │  └─ ee72e0f0ceeca3d63df6dfa8251d5d63906468
│  │  ├─ 0e
│  │  │  ├─ 0776faa11a1f3c303c394fd843f1d34479770c
│  │  │  ├─ 404951dba452623a056c7a0bb9ec1ffaf02d05
│  │  │  ├─ 75123c6438295388562aeb509ab8a38ed73798
│  │  │  ├─ 9071ad435df7abd03bf08cbae9657cd7b0798e
│  │  │  ├─ d4d76072a11e673e723d64c21e000e12f58d26
│  │  │  └─ ea3d3f8dab758b3c066fca57ffdd150264d4c7
│  │  ├─ 0f
│  │  │  └─ 443ba8e5f116dd523b46d872af64ea617b1f85
│  │  ├─ 10
│  │  │  └─ e25defd46a5c05165898b94ce15492003b5fac
│  │  ├─ 13
│  │  │  └─ 3e4862bef6baee6b53c10f0a73e714e6a40d36
│  │  ├─ 14
│  │  │  └─ d2d5b0986a7b04f9cb518e76ea1bf709cbd8c4
│  │  ├─ 15
│  │  │  └─ 720057809d1f7eff705bde68894e1c3a631c6f
│  │  ├─ 16
│  │  │  ├─ 85289896747b38bf4e6a481eee03599be543da
│  │  │  └─ 8a92186a8ef8773364cf70ee4635cd956d25c1
│  │  ├─ 18
│  │  │  ├─ 775cde13386da71f3bcbc3eac268adde0bc6af
│  │  │  └─ eb51cd151b041bf70153675cbea52b32104573
│  │  ├─ 1b
│  │  │  ├─ bd76a3757ae6b547600e609225e46bfed2f41a
│  │  │  └─ d97cad35354543f901006d673590cae034d10d
│  │  ├─ 1c
│  │  │  ├─ 6d9d45351ebcbd6c13e199d8297f883b149bf3
│  │  │  ├─ 8e5ac468c43d7e1ba57a57081c4b5d39145455
│  │  │  └─ e769eb7c865adb3124f78d108b6025fde6fc5f
│  │  ├─ 1d
│  │  │  └─ e77c0a5177ca06255a55bbd99a83ef0e637aa9
│  │  ├─ 1e
│  │  │  ├─ ebb16c5480270a46c2947c73b31465ad77e8fb
│  │  │  └─ f5247ff1c912377cb8c81de0ec36006679f430
│  │  ├─ 1f
│  │  │  ├─ 5fb3f91d9cf2a8db7eb3ef24eae4f0faae44bd
│  │  │  ├─ d54e749c28a8fc914a2f30f5dbbc664715d55c
│  │  │  └─ f40b9fbde1a94c6b261e6b0d47892b74296930
│  │  ├─ 20
│  │  │  ├─ 5a980d4e11e4c96c9922ee83bb30efdcfd3693
│  │  │  └─ b8df0fa225c696b4be025332c9c177ccc2f9b8
│  │  ├─ 21
│  │  │  ├─ 1b253dd2c3ecdf7f28e4f90c7c4930528f22b2
│  │  │  ├─ cd1287c0ef9d4b94715f14e622869229fbfe4a
│  │  │  └─ d968bca3e9a338466e37ef210f16d0551a27c2
│  │  ├─ 22
│  │  │  └─ 4f45893b9a8af2938d7505b95ad9619b87a5b0
│  │  ├─ 23
│  │  │  └─ 0cb2760a7ef295741112308887e10025db50bd
│  │  ├─ 24
│  │  │  ├─ 99732d7950326b5ebbeba1e40ba96d6ff15c95
│  │  │  ├─ c8318abec016438c4579f050c6e05e146cd6f3
│  │  │  └─ e32733a582174cdb6dc5924adfe0e3ac1652a2
│  │  ├─ 25
│  │  │  ├─ 3538cab6565b6600addc7c7b85df6e6f41ebb3
│  │  │  └─ dfcd529339c2b875995e5a11a92826b0d877a9
│  │  ├─ 28
│  │  │  └─ d0702265f6df99fca06e5b7bfbd9c22b74344e
│  │  ├─ 29
│  │  │  └─ 4e39c61f7d33612c0b5f937378022b97655b5f
│  │  ├─ 2a
│  │  │  └─ 39270174f6b5a4d9b07560c5f93e853bb40e66
│  │  ├─ 2b
│  │  │  └─ 3bc05d4451b77ddceeba7ce6aab39aab6a215f
│  │  ├─ 2c
│  │  │  ├─ 4defc77ceccf8853862c92efabd61191f3171f
│  │  │  └─ dd9f914da21ab8684dbd6f296a06fa22206d1b
│  │  ├─ 2d
│  │  │  └─ d215ac483943058a113474d0a5c7c91e512fe0
│  │  ├─ 2e
│  │  │  ├─ 115352deb8d5d4e771568a0e7ed951a717cc02
│  │  │  └─ f192b9685e1d3abe7c9d509dca7e9fb63c3505
│  │  ├─ 30
│  │  │  ├─ 3fd2b390de43213b6d965f609d898f1738a810
│  │  │  ├─ b2930e2c25b240b686d26537b3f63af3999013
│  │  │  └─ d7ea1ac9c5bbb79e4025cc85b3416638203de4
│  │  ├─ 31
│  │  │  └─ 4c30578dca1e2d0f5d28f825d64022b114e4ec
│  │  ├─ 32
│  │  │  ├─ ab2ab946b2850113073086f165eb6bda9dd2f3
│  │  │  └─ fae2b4501daadfbc4fe371bac717417253ea5b
│  │  ├─ 33
│  │  │  ├─ 5ca656169767c664c641af489d5d49b8a7e841
│  │  │  └─ ed068819da0a9f11c77e35f5825d10a4722bc0
│  │  ├─ 35
│  │  │  ├─ 65b02ef40783e67f3ed7f7496926b721333d03
│  │  │  └─ 8d83deda6fe11efa027758f05d579b22ec8423
│  │  ├─ 37
│  │  │  └─ 2c2fea87786e7fc6051a917cc7e3490101dcfb
│  │  ├─ 38
│  │  │  ├─ 8a8ab3ea3f1a37c23f62f07e8cba934896acd5
│  │  │  ├─ 8a8c43336154ce174fbb37e4ebb0fae50c0d6b
│  │  │  └─ b324fe2d6ef1a6ca62bcf52a66d7d6b994b46a
│  │  ├─ 39
│  │  │  └─ 1d70d5da111cdc2b6d79f6369c6ffc122b0ac3
│  │  ├─ 3b
│  │  │  ├─ 40bd3f3ccdcb9ae053f9973cdd8607f9cd3be7
│  │  │  ├─ 8e83306d5a5863696575decf553df9650bea7d
│  │  │  └─ a1b51bcf5f505f38b6a9b4fad486d01ba73c01
│  │  ├─ 3c
│  │  │  ├─ 5e7a404c84b8a9ebb94b85f6b3ccd76e8fc2fa
│  │  │  ├─ 8680afe25deedff5c34dd04b63245ac32a50e4
│  │  │  └─ f6435df47f581136c682d52ad04fbc3e9cb9f8
│  │  ├─ 3d
│  │  │  └─ 679cc29e8a55ae9a67a9dd99d7c1f8d3d52f30
│  │  ├─ 3e
│  │  │  └─ 77653dbed9afdc114651c1b52166e97a075bbc
│  │  ├─ 3f
│  │  │  ├─ 8c9aa0b02424676e57085e4fde1fd927d51721
│  │  │  └─ 98c09ae0b38f7f8ea03f7ba06aad8b9be1a064
│  │  ├─ 40
│  │  │  ├─ c96d5745a9d4ee23ba15b36c63625fe0ed1d9c
│  │  │  └─ e7db3854e04cb38d8799ab5b84f6f3873ebdbe
│  │  ├─ 41
│  │  │  ├─ 65a3840700d36286ffd37dffcfe1e8e3b1ba99
│  │  │  └─ bb33ba54f88d7187ccdba7d990114e0a27f197
│  │  ├─ 42
│  │  │  └─ c230bdc4df6285fcf7f05042cf1cde75be9196
│  │  ├─ 43
│  │  │  └─ d1e2cbc1029d60ac2a0e7914707a448f4d5f0e
│  │  ├─ 44
│  │  │  ├─ 722611cf9ad289c7407695d596bc69f546945d
│  │  │  ├─ 94faccaa8983f6d5db4df16faa0ece96523909
│  │  │  └─ 996274eb179f5e94ca0005752d32984f8b273e
│  │  ├─ 45
│  │  │  └─ 683f7f552a551ef46f465ca9630c25f0f9ba0f
│  │  ├─ 47
│  │  │  ├─ 00d2c9211ba749d77ebc6cfbdce10332ce28b7
│  │  │  └─ 3c3a62d0c66da3a82bc3c03d5260bc2b30e0e4
│  │  ├─ 49
│  │  │  └─ 43305ab2773450bbf0fa534bc34239acc7d5dd
│  │  ├─ 4a
│  │  │  ├─ bfb25abd298760c718dedafb0b5ea279b3157d
│  │  │  └─ cc4e68db0362fbd5b057066a7775090e676c19
│  │  ├─ 4b
│  │  │  └─ 93f8685565b27ea902a4e83880c707159efc90
│  │  ├─ 4c
│  │  │  └─ 68e47d0284e5c2c143c6bfcfec749718f445ce
│  │  ├─ 4d
│  │  │  ├─ ca7b8e681030aaf80609ede0571a89da3912c6
│  │  │  └─ dc44b46cec35894b4f331b00c113427d52b977
│  │  ├─ 4f
│  │  │  └─ 40b8fb778e6285683e3dc7ffe763f9f9ea1531
│  │  ├─ 50
│  │  │  └─ be79d2c83a6216de81ead927269f18304d5e3b
│  │  ├─ 51
│  │  │  ├─ a4b619065812d084e3e65732bd81dd06a97f2b
│  │  │  ├─ b67d1dfa17ef42432230c9c210b6afdfa429fb
│  │  │  ├─ d45f9a94c2cffe00a72eef9d25daaa56c7b01e
│  │  │  └─ eefa8c0b3599713929b604f9fb9ea84353be6b
│  │  ├─ 52
│  │  │  ├─ 243d35ae152a9d2e55e60392f4d297f022f1c9
│  │  │  ├─ 3b64f266a928ce1af8ee33d3246f7739c63db4
│  │  │  └─ ef3a1008eda7cd31c74da61709a0b42fa0636b
│  │  ├─ 53
│  │  │  ├─ 8c8338ebe54e8620b7e99cec42b5dec33fb7cf
│  │  │  ├─ c36f498c182f738458b10c62984f7401dc5a3b
│  │  │  └─ f6cae82ae83858db225980b7206d77db166c08
│  │  ├─ 57
│  │  │  ├─ 33c4cfe9b4ede419df3c92e3262130916d4d72
│  │  │  ├─ 6f9df57c72400b8cebf1978df15e2642cf6564
│  │  │  ├─ eb3ffd8680ae52c2631cadf0a2394c28b547a7
│  │  │  └─ f4fa5798a88988b87620dcc3b684fcef64e5c0
│  │  ├─ 59
│  │  │  ├─ 36341f33f8818f26e04e52a0095a09601608e8
│  │  │  ├─ 8ca512265fb204053633b3e2fdaf197265195b
│  │  │  └─ e0035208c7a20259c8090c6bba029d88d0183c
│  │  ├─ 5a
│  │  │  ├─ 166ce78e6b1484365f4b6878c1bd6012195134
│  │  │  ├─ ab0ca72394917cbe02734f2e715312785eb651
│  │  │  └─ cf25b380f8586490348b43d96080372c038dd4
│  │  ├─ 5b
│  │  │  ├─ 12429e1193dec0c175deaf82c5da767461a562
│  │  │  ├─ b9016316359642311727921762f352534f766a
│  │  │  ├─ d3872b4260876f0cb232ec1728ea9bc9ef0b3c
│  │  │  └─ f3a411c03b2db16476435b3341b8f9e6153665
│  │  ├─ 5c
│  │  │  └─ cf88242a9a811fc2778ea7ffe7c8b698b8a0f1
│  │  ├─ 5d
│  │  │  ├─ e7d9387232e725519ee811b9bf78f7b096cbfd
│  │  │  └─ f7f650b80fef281199e7d1c6bf53c6a5280802
│  │  ├─ 5e
│  │  │  ├─ 3dcf88693404fcf2765ffa01c8fe54d460c0a8
│  │  │  └─ 7867a03904dd8c6119a5e5246fc99cd8c62850
│  │  ├─ 60
│  │  │  ├─ 1b49a6078d3fdaf08e726a413494a61fe69225
│  │  │  └─ 1c44b8df46208346ed53eb46d59e590d61a28e
│  │  ├─ 62
│  │  │  ├─ 74a80726b44f69ca8e6d6e9aad9a30b541a239
│  │  │  └─ 912fa891442f61c83e40d1a2a2f2494e30dff4
│  │  ├─ 66
│  │  │  └─ d0d04472fc1f8e96999b5527213ac93075bad1
│  │  ├─ 67
│  │  │  ├─ 07e3ec1a9e9763fe0a4c43ab4723b89a442f9e
│  │  │  └─ 7a294680e3ea5063d52adb464eeb14235abb15
│  │  ├─ 68
│  │  │  └─ 8359a9300357ec2d6f2d2e32cc1c877a1dc821
│  │  ├─ 69
│  │  │  ├─ 38de1fd2681ec4dded793c35e543a8ec16b116
│  │  │  ├─ e16044f8b6fd8ae2a5b5401b161b2eb21dc837
│  │  │  └─ e48abfb3928e8586151b639977867ccc4b638a
│  │  ├─ 6a
│  │  │  ├─ 200278efa59f4fca18a8accc41f0754b19200c
│  │  │  └─ 87a6889f4e82ea90fd1a1702bccbe51781667e
│  │  ├─ 6b
│  │  │  ├─ 125f9619cbd78e501277268f72d52a8ad54335
│  │  │  ├─ 30bf9c679fcc7f0ea2e0d05fc4dbcc129971af
│  │  │  ├─ 58a02031e4d22c34811fa794ddc754a4512546
│  │  │  ├─ 7d67bcdb1b6aee1bd39898da2b849677721a39
│  │  │  └─ d3b81466ed255a723ff270ff4d995552910bac
│  │  ├─ 6c
│  │  │  └─ 66fe8c6c069ecf1e6b3d48d0666334220e709f
│  │  ├─ 6f
│  │  │  └─ 471fb5e4c7edfdb303b705c2c18d03fe267d78
│  │  ├─ 70
│  │  │  ├─ a2296b57b0e6ec5135702e38abc7ea60e459d8
│  │  │  └─ f7fe477acd86bfc4f26c62909431e40f92ddd2
│  │  ├─ 71
│  │  │  └─ 9d5ca05c058f675c2fd03028a2f14950078101
│  │  ├─ 72
│  │  │  └─ 16afbdc54837a8f5502f2c9ff971e6fa01997a
│  │  ├─ 73
│  │  │  ├─ 29c461d17ac1f7e0bd09dc1d58c25b264ab2c4
│  │  │  ├─ f0443d5a526e5484cc264eb601197b73569a3c
│  │  │  └─ fe8dcb422b4e38d7f641168354b34a801f7264
│  │  ├─ 74
│  │  │  └─ 7b4e59645a7561ec2c76794bd0be2a128cfa29
│  │  ├─ 75
│  │  │  ├─ 0e8c148d63c8ac6ce15e8a20051a5c59b00ade
│  │  │  ├─ 74e6ae1fccbc69aea6d7f52813b45557b96e03
│  │  │  └─ af3b01c7fccbac603d47305271be1ebdbc48fc
│  │  ├─ 76
│  │  │  └─ 5899ff029d47a1cb42a4ca627fa79138e2b1af
│  │  ├─ 77
│  │  │  └─ 18ba26036c148d8b77b1a667e68e96cca5f2ff
│  │  ├─ 7a
│  │  │  ├─ 04322d12beb385b5340b575e7ea3f444ae52c5
│  │  │  ├─ 42dfed3b9261d25d778c1196e3699ead516a42
│  │  │  ├─ 6ea47daefbbdad717824271c63f169b754137f
│  │  │  ├─ 7a861aef11eced275e69653f0f8cc69459dbc1
│  │  │  └─ de41eb83605dc2575308bbc2cc5578c1707c87
│  │  ├─ 7b
│  │  │  ├─ 93f96fde205e0187d2a352ab2f4253a1db2906
│  │  │  └─ e85dbe8df0c2a76a86e34ebe541f666153ab7a
│  │  ├─ 7c
│  │  │  ├─ 79d3e5ef3f3ecbe5d250106b3aa5b6170ded78
│  │  │  └─ 88cf69e680d40b09e1606d9a57e32bbc349c6b
│  │  ├─ 7d
│  │  │  └─ 833f31b0db93f2fb1287e5179fab8686761fbf
│  │  ├─ 7e
│  │  │  ├─ 170229efc529753b7386ca19f3d80b7b8d803d
│  │  │  ├─ 4d0830eb616d391e4414081624f163e7d4befb
│  │  │  ├─ 8828145e0c550e0d9c3c585350430c7f1da59f
│  │  │  └─ fef71df05c3d529cad519be99b183e110b0547
│  │  ├─ 80
│  │  │  └─ ae5c8bbeb11d1a4843d25b4de672d7ed408d9f
│  │  ├─ 82
│  │  │  └─ 837fddfbeed9cdde0f3c7133b585d98b3fa28b
│  │  ├─ 83
│  │  │  ├─ 02f1f46899e4830a8f38a29d828e07cf409f1a
│  │  │  ├─ 3807ea9bf28b8b6892bc5be7aea130aa44c9b3
│  │  │  └─ ad6995510832bba236da63245f10194ebb9238
│  │  ├─ 84
│  │  │  └─ 445487779d7565d885d48c182d6f6b91816a62
│  │  ├─ 86
│  │  │  ├─ 40c07ba194fa6a92642d121bab316378eaa651
│  │  │  ├─ 8039712e676cff52af74915d0e9ae03a4be86d
│  │  │  └─ 92b927ce26470cfd42c8ffa40d57a9c206c7f3
│  │  ├─ 87
│  │  │  └─ 386311410ef919c8b7e91d2883aab92c18077a
│  │  ├─ 88
│  │  │  ├─ 7a68684693443421b03ca671c1dd0211c3fe38
│  │  │  └─ a889ee28974966c10ffa36b93d4346cd89cd9a
│  │  ├─ 89
│  │  │  ├─ 5559c836d1bee4f9f1627b7ae04433fa34ed79
│  │  │  └─ eea25a909ec9f738dcb9ae53b7d1ae2f44efac
│  │  ├─ 8a
│  │  │  ├─ 21631502f185ddd216ec1ce9cadef6d33cb10b
│  │  │  └─ ac9a4c8b17db881f28483cf7a55338ec40c079
│  │  ├─ 8b
│  │  │  ├─ 137891791fe96927ad78e64b0aad7bded08bdc
│  │  │  ├─ 48f4624889a0f41ddca483eb961483203fe31c
│  │  │  └─ 89076691537dccd34405fba43be5364bcd021a
│  │  ├─ 8c
│  │  │  └─ d76dd55168920099634761938661968f7cc9a2
│  │  ├─ 8e
│  │  │  ├─ 2088c23fa6e71710009df33156f0e59a0790ad
│  │  │  └─ 468d55172cedae353fe363260cc9d1b14bb092
│  │  ├─ 90
│  │  │  ├─ 4bf8db8463ee3f29bc9c01c9031792c6d67cd1
│  │  │  └─ 839ddbd507f4fc468e1c0682c1b1370b582474
│  │  ├─ 91
│  │  │  └─ c5b5e819003d89d9063a7fb2f14ea6138559d3
│  │  ├─ 93
│  │  │  ├─ c235c444fa007fd58a93a1bc46543a70c71a6d
│  │  │  └─ cd01b94fadc75690b7d4c8042cdf776daf7736
│  │  ├─ 95
│  │  │  ├─ 1e65cc11ca18d29506aa6c43c5255b1b58fe49
│  │  │  ├─ 6536de554af447292cf965ec13a0a27cb45163
│  │  │  └─ b467980b08085bed8f267a5b085301b2d53d3e
│  │  ├─ 96
│  │  │  ├─ 116f2ebce6b055a10092395f217ec624436f73
│  │  │  └─ e5938b0d223eee05f96415f76e0476fc773944
│  │  ├─ 97
│  │  │  ├─ 203f17a8a64920a90b42c47e654f7daa0450b7
│  │  │  ├─ 48712ce9a20d98e59dec8102dfd0909bd30b61
│  │  │  ├─ 6c6df832982efe21b3b0921fef284ce78002e6
│  │  │  ├─ a47fc3dc2152085126dd7ed9cb7c8e5eff2658
│  │  │  └─ e0ea18f02a0c37b83b5cde8e078d7fd3d7f10a
│  │  ├─ 98
│  │  │  └─ 6a95ce37762b7207ca5fe74237cc565de264b4
│  │  ├─ 9b
│  │  │  ├─ 9999ec9a61012e3745650784c03ffff5886615
│  │  │  └─ bdf43923dd1005b7d412478e47ca13de1e4cd3
│  │  ├─ 9c
│  │  │  └─ 35e7e8ce9fc9ff9955261f842d65ac9918aded
│  │  ├─ 9e
│  │  │  ├─ 2299ce87921f64807a8f99671aee5206a5f00a
│  │  │  ├─ b9825f3ea771c814649b2a356a84b584b609b0
│  │  │  └─ fcac4d2622fbbcf6e1e0e1b9400e0948c9139d
│  │  ├─ 9f
│  │  │  └─ e191e5549ec0761e873d658824aeb40d4d2e63
│  │  ├─ a1
│  │  │  ├─ 8453e593117ebf7b1ebe1a4d863ad28727e377
│  │  │  └─ bedee186ee41b7676b04df38b06414db89b6c5
│  │  ├─ a2
│  │  │  └─ fe78fe19bc12de8da96ef304ba7506582a0501
│  │  ├─ a3
│  │  │  ├─ 3ef29b8d8a33c39536253ac4fc00e6ee7f130a
│  │  │  ├─ 5f25f59574c19c93f8fe83988e35703989c5a7
│  │  │  └─ eba63a34cd3d16e3078cb20cab3d18665691eb
│  │  ├─ a4
│  │  │  ├─ 20f2013d82b35db97325d7d316a3c58173b32b
│  │  │  └─ 4df59949066cb34ccfc32d238f68fc7e0e4da1
│  │  ├─ a5
│  │  │  └─ a062cdc6ae9fce6ddd571827255df0eabf9318
│  │  ├─ a6
│  │  │  ├─ a5a29e3b619095fa39d72b5b62a964fb110ac9
│  │  │  └─ d43cfbc405a7b36f9c1b707b0425a1e1a14b5b
│  │  ├─ a7
│  │  │  ├─ 56e2ae1a26a518ddb961c743ebb90d9457e2ac
│  │  │  ├─ 5dc17c9ee249bf36bc71889a7e425d7d774446
│  │  │  └─ 9a51a7bb573ad6c67067238676d8b4e84fffc8
│  │  ├─ a9
│  │  │  ├─ 06b347fee6fecedea5ac057a8b2e078ce2abad
│  │  │  ├─ 3c44bb1b5baa208a10ab6b354706d4ded01843
│  │  │  └─ 8a2d445660a9e07cc852b27b8a1881ebd3b614
│  │  ├─ aa
│  │  │  ├─ 02e7b84dd6f5fc0cec68bc80f73227a3f991c0
│  │  │  ├─ cd3f33a3c2fc97bd5fcdcc23a1f754af2a7253
│  │  │  ├─ d707039d4166e2c1a2548d5be76a0de0c06b26
│  │  │  ├─ e0664164ca5428bdfcd9fc5c9b24a00af849d0
│  │  │  └─ e7928deebf6fdc3efc989c10f863a1db79aa9c
│  │  ├─ ab
│  │  │  └─ d67f6a0508a7de9f564d66b1e5f7c808437ea1
│  │  ├─ ac
│  │  │  └─ 726394a015e77924776b3ab266da7c9d635d2d
│  │  ├─ ad
│  │  │  ├─ 8e80ccd1e8bb79fda494c1684c6bc55f5570c2
│  │  │  └─ fc79cbd39a614786f3c55b7cb83febb8a88ff5
│  │  ├─ ae
│  │  │  ├─ 6cc8f6d0c1097a50f5a1fbb01cb79484d8a224
│  │  │  ├─ 78bc90e60e1f7ed2fcbfda358b9a8c365fdbc3
│  │  │  └─ f94188f10df50a6922f0bfadcb9cc61d15d7b4
│  │  ├─ b0
│  │  │  ├─ 34bbe248c3ce6a223a9f8f9167255b0b22ccfa
│  │  │  ├─ 8b8786d3bfc3fa4b82e9bd917a2a6c8ce1e2e6
│  │  │  ├─ b5cdec7a7da013613ce37eaf248260dd41d592
│  │  │  └─ bee7776db1dc572a63dcc450ead9cbe1e164cb
│  │  ├─ b1
│  │  │  └─ ef13f376d7301b4653e469bd215c7e6e68845a
│  │  ├─ b2
│  │  │  ├─ 2260f9ba67247842636145088e53f973d79a01
│  │  │  ├─ 691c449d34e701d071d1cde76ebf0498474e91
│  │  │  ├─ bda5426a78586e15effe7ce0d793872f127790
│  │  │  └─ dd141fcaf347bd9cac652aa7bfe282d053d6b1
│  │  ├─ b3
│  │  │  ├─ 30b40dc7c23128fb56f4599caf6e2df4a1bdce
│  │  │  └─ 60a363e3d91fe3ce8dfa718e5482388fbe130d
│  │  ├─ b4
│  │  │  └─ 7cbb3dc3bca72e61fc311a235289eb93e6100d
│  │  ├─ b5
│  │  │  └─ ce1a97adc4fee32b4c477f9d9b3f305ede93e7
│  │  ├─ b6
│  │  │  └─ a3ec52032e002b6fee9dbcdc54603efb27ecb9
│  │  ├─ ba
│  │  │  └─ 9103d058e27128fae720213b3bbc2a36feb1f3
│  │  ├─ bb
│  │  │  ├─ d52890f5d1a60c365dd56b81025a618a41f379
│  │  │  └─ f9b91bfdffba3206ce136dca665af2cc9a8feb
│  │  ├─ bc
│  │  │  ├─ 830e30529901165dc3b71c1ae082d564b7568d
│  │  │  └─ 99271afa7b3c655a5782b7d524b23b1114056d
│  │  ├─ bd
│  │  │  ├─ 6a48f389ea94cc3fcefc29a9230ef2cff592a1
│  │  │  ├─ 75c94f5c9af812225bd9f2d40ce117c7fca45c
│  │  │  ├─ b0eb741d48aac44d2581f0d87e583ed9ce564f
│  │  │  └─ d5312dc19346c429e39ea05da2b532ac216470
│  │  ├─ c0
│  │  │  ├─ 32d53354cfb9821eb12589d7496f586b4d8dde
│  │  │  ├─ 83f39e02e94608f480971a00d7cbdd08ec03cd
│  │  │  └─ 8e48fba84d74500ba0ca26976652cd4a32d3eb
│  │  ├─ c1
│  │  │  ├─ 38933aed645584a841bdd767f6d4af882b1046
│  │  │  └─ ff362491b92d2c365c519025e7c25a7e92f349
│  │  ├─ c2
│  │  │  └─ a52e48170f48d827571c046a75ccdc861bb378
│  │  ├─ c3
│  │  │  └─ cb81e24db5d812381eb288b9be5d4141bba089
│  │  ├─ c4
│  │  │  ├─ 9a8c477340537a9b3487ef131cf175f5d46c01
│  │  │  └─ a19d94e39da7f072f51d89da0e0edce858ee2a
│  │  ├─ c6
│  │  │  └─ 32f99c7ef2d6980cc1b8a25dc1050a38457758
│  │  ├─ c8
│  │  │  └─ 48846a32d8e0a07d9709962dff03257d70b531
│  │  ├─ c9
│  │  │  ├─ 9460ae49124490cc051eda016d2fb6e671775d
│  │  │  └─ c8ce36745f2d41e4325358e0e81b169da87aab
│  │  ├─ ca
│  │  │  ├─ 7d06b2f957d668600ae5ee653180028234343a
│  │  │  └─ fe9e6b5f254cf4e33aa62a822612a318679b54
│  │  ├─ cb
│  │  │  ├─ 2096ca545674150ed0698db26f056abdb41f93
│  │  │  └─ d4da093e1ea8973650f207a14534c4b2d84e3f
│  │  ├─ cc
│  │  │  └─ cc43490c5eb89ff161259ce7523cc38209c030
│  │  ├─ cf
│  │  │  ├─ a5d5eb0d09c5870d643d4436a8b228ec3961f4
│  │  │  └─ f28224e03baa550a06fd5e835acee999345603
│  │  ├─ d0
│  │  │  └─ 88d0f24eb7675cc8f9ed7d7ea05133e240054c
│  │  ├─ d2
│  │  │  └─ ddf9e8c2736606e42406653d71fba9c16276c8
│  │  ├─ d3
│  │  │  └─ 19263c1d3d5f843e9ba5b1920e5bc053bf3584
│  │  ├─ d4
│  │  │  └─ 32481d50f570779e035f4ec0189d551e416ddd
│  │  ├─ d5
│  │  │  ├─ 0ceaeaf1ae42799e218a43cd0c58314f53809c
│  │  │  ├─ 48443538dd67566995b979eda5c2f103380c8e
│  │  │  └─ 97df96894f671eb1941549fd1f7dd8e9fd4a3c
│  │  ├─ d7
│  │  │  └─ b397bba032d2a3f425a074e03d5a3f8a63149c
│  │  ├─ d8
│  │  │  └─ eb503ecb3df34e375611dfcf26ce0afec4eb0f
│  │  ├─ da
│  │  │  └─ c5fa084aef1ae02403a0a86f54b0210d06e804
│  │  ├─ db
│  │  │  ├─ 2fb1e0d1780d6fa5f19313989b735be7629688
│  │  │  └─ 958b6df96003c12b0d5a83ff4faed0c59f048d
│  │  ├─ dc
│  │  │  └─ 32d9d140097aae1a86a718aadd881de526a3f0
│  │  ├─ dd
│  │  │  ├─ 967b9099696cb234cf0a7dd00ad2ce48068a02
│  │  │  └─ ea380e17ca9f3e08d1a3b4fc65ea2dc6efd629
│  │  ├─ df
│  │  │  └─ 321c6e01b49cfba46fd5d81fcc2e28d6e5131b
│  │  ├─ e2
│  │  │  └─ f0218e223c389f9575b42079b6c7533e613e22
│  │  ├─ e3
│  │  │  ├─ 293c45dffffca9216eaab3a0a2161c2000a143
│  │  │  ├─ 4ae03b8712120c924fc3118deccec99921dc3f
│  │  │  └─ df3d4af206e87e8272cb7ba874d24caf5cbbf0
│  │  ├─ e4
│  │  │  ├─ 35e01bcfcf4b923a0ad9460eab77acc53fc86a
│  │  │  └─ d47387f067ad0791aebe80bcf7fcc1e6606e96
│  │  ├─ e5
│  │  │  └─ 8a92b0b42fc0d383c4c14afddb7f817e50aaa1
│  │  ├─ e6
│  │  │  ├─ 721fd94aa28ef156891cea1d9bc4ea2a0a3df0
│  │  │  └─ 9de29bb2d1d6434b8b29ae775ad8c2e48c5391
│  │  ├─ e7
│  │  │  ├─ 1acf0bb1054bad0f5db6126a6533220097f9e9
│  │  │  ├─ 8027e1903779636a50f036bb7c23b802ee60a2
│  │  │  ├─ 988434cf9fa3a4baeb76a1f6b75ca1855eb0b4
│  │  │  └─ be930eb1c2d007b54708234ce8aa6f0e76f5df
│  │  ├─ e8
│  │  │  └─ 15fffec1322ff465d79b4c22d18046d9c93465
│  │  ├─ e9
│  │  │  └─ 7c6446c87e3bc8b140213be2e8cc1b5e4bd217
│  │  ├─ ea
│  │  │  ├─ c8f813f258bbbeed45214c46463616775adc87
│  │  │  └─ ce5b0310502441f95b60cb5b4119db8cd22458
│  │  ├─ eb
│  │  │  └─ f21542bde192e1960c63aa27d58da2d1357bbb
│  │  ├─ ec
│  │  │  ├─ 8d1385ee058ba048fe58387e48f957fbfd76a6
│  │  │  └─ d0ff989f19e1caeecf53a4de429d6890b81f75
│  │  ├─ ed
│  │  │  └─ d6bd48bf10d9627ca47b1c9fa2406af943957f
│  │  ├─ ef
│  │  │  ├─ 46847b646964eac00e5aa6aa58901ebbcae626
│  │  │  └─ fd3f5c69c1de6ab2d05c40afb57712ebdbb3fb
│  │  ├─ f0
│  │  │  └─ 55bca09f57eb24a87ffdf30cdb013bd0725254
│  │  ├─ f1
│  │  │  ├─ ddc840ea8d63c0e83799a83fe2d8c69cf8469a
│  │  │  └─ e783b22b6addcc8701b15d81b95b0fd2753335
│  │  ├─ f3
│  │  │  ├─ 8d1eb03ac9d6dbeed0528ea6cd1454e87bd597
│  │  │  ├─ 97af0bf19c218ccea19b3d2936b10a9df221fe
│  │  │  ├─ bafb83d1047bc7ea90ad8d10f9d681ddac58bc
│  │  │  └─ fd96dd80bd8a791567b10ab8f9d1ce2730dcf6
│  │  ├─ f5
│  │  │  └─ 7ffcbcfc4404e8f81f8ac17af7bcff4527540e
│  │  ├─ f7
│  │  │  ├─ 0c70eedf948769cd6a641e492b2d143e78d2e0
│  │  │  └─ dda74cc87571e94f12b28751481004170709ea
│  │  ├─ f9
│  │  │  ├─ 9e9471617ec46e596d0ed0bc5690d75426845c
│  │  │  └─ c4a76ea1dba09c696c7cc0544a1c803b36598d
│  │  ├─ fa
│  │  │  └─ 8d8f5855ec6fc660f8b5cfb1b24777a1c737ca
│  │  ├─ fb
│  │  │  └─ bc1eda9abeaab17c377c42cc298d27a90ef7d9
│  │  ├─ fc
│  │  │  ├─ 7787299fa95b481e28fba5aa2d5bf81f61316a
│  │  │  └─ edc8ab499a8b30178167efa5a814c840a946e2
│  │  ├─ fd
│  │  │  └─ c6b8877489f784d31eb397d4867e8587e3c76a
│  │  ├─ fe
│  │  │  └─ 31549008c6808371d2c2435d8c8e7d706d4e75
│  │  ├─ ff
│  │  │  └─ 7239677cd56b903f267ab00a0e9d4f9487e233
│  │  ├─ info
│  │  └─ pack
│  │     ├─ pack-a3b315c20e464919692d4cf9237d2ce52fbf4b6b.idx
│  │     └─ pack-a3b315c20e464919692d4cf9237d2ce52fbf4b6b.pack
│  ├─ ORIG_HEAD
│  ├─ packed-refs
│  └─ refs
│     ├─ heads
│     │  ├─ development
│     │  └─ main
│     ├─ remotes
│     │  └─ origin
│     │     ├─ development
│     │     └─ main
│     └─ tags
│        └─ M2
├─ .github
│  └─ workflows
│     ├─ main.yml
│     └─ pr.yml
├─ .gitignore
├─ app.yaml
├─ gradle
│  └─ wrapper
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ LICENSE
├─ README.md
├─ settings.gradle
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ ch
│  │  │     └─ uzh
│  │  │        └─ ifi
│  │  │           └─ hase
│  │  │              └─ soprafs24
│  │  │                 ├─ Application.java
│  │  │                 ├─ constant
│  │  │                 │  └─ UserStatus.java
│  │  │                 ├─ controller
│  │  │                 │  ├─ GameController.java
│  │  │                 │  ├─ GameWebSocketController.java
│  │  │                 │  ├─ SpotifyController.java
│  │  │                 │  └─ UserController.java
│  │  │                 ├─ entity
│  │  │                 │  ├─ Game.java
│  │  │                 │  ├─ Player.java
│  │  │                 │  ├─ Settings.java
│  │  │                 │  ├─ SongInfo.java
│  │  │                 │  └─ User.java
│  │  │                 ├─ exceptions
│  │  │                 │  └─ GlobalExceptionAdvice.java
│  │  │                 ├─ repository
│  │  │                 │  ├─ GameRepository.java
│  │  │                 │  ├─ PlayerRepository.java
│  │  │                 │  ├─ SongInfoRepository,java
│  │  │                 │  └─ UserRepository.java
│  │  │                 ├─ rest
│  │  │                 │  ├─ dto
│  │  │                 │  │  ├─ GameGetDTO.java
│  │  │                 │  │  ├─ GamePostDTO.java
│  │  │                 │  │  ├─ UserGetDTO.java
│  │  │                 │  │  └─ UserPostDTO.java
│  │  │                 │  └─ mapper
│  │  │                 │     └─ DTOMapper.java
│  │  │                 ├─ service
│  │  │                 │  ├─ GameService.java
│  │  │                 │  ├─ SpotifyService.java
│  │  │                 │  └─ UserService.java
│  │  │                 └─ websocket
│  │  │                    ├─ dto
│  │  │                    │  ├─ GameResponseDTO.java
│  │  │                    │  ├─ JoinRoomPayloadDTO.java
│  │  │                    │  ├─ LeaveRoomPayloadDTO.java
│  │  │                    │  ├─ ModifySettingsPayloadDTO.java
│  │  │                    │  ├─ PlayerSongInfoDTO.java
│  │  │                    │  └─ SendEmojisPayloadDTO.java
│  │  │                    ├─ mapper
│  │  │                    │  └─ DTOMapper.java
│  │  │                    └─ WebSocketConfiguration.java
│  │  └─ resources
│  │     ├─ application.properties
│  │     └─ META-INF
│  │        └─ additional-spring-configuration-metadata.json
│  └─ test
│     └─ java
│        └─ ch
│           └─ uzh
│              └─ ifi
│                 └─ hase
│                    └─ soprafs24
│                       ├─ controller
│                       │  ├─ GameControllerTest.java
│                       │  ├─ SpotifyControllerTest.java
│                       │  └─ UserControllerTest.java
│                       ├─ repository
│                       │  ├─ GameRepositoryIntegrationTest.java
│                       │  ├─ PlayerRepositoryIntegrationTest.java
│                       │  ├─ SongRepositoryIntegrationTest.java
│                       │  └─ UserRepositoryIntegrationTest.java
│                       ├─ rest
│                       │  └─ mapper
│                       │     └─ DTOMapperTest.java
│                       ├─ service
│                       │  ├─ GameServiceTest.java
│                       │  ├─ UserServiceIntegrationTest.java
│                       │  └─ UserServiceTest.java
│                       └─ websocket
│                          └─ WebSocketConfigTest.java
└─ system.properties

```
```
sopra-fs24-template-server
├─ .editorconfig
├─ .git
│  ├─ .COMMIT_EDITMSG.swp
│  ├─ COMMIT_EDITMSG
│  ├─ config
│  ├─ description
│  ├─ FETCH_HEAD
│  ├─ HEAD
│  ├─ hooks
│  │  ├─ applypatch-msg.sample
│  │  ├─ commit-msg.sample
│  │  ├─ fsmonitor-watchman.sample
│  │  ├─ post-update.sample
│  │  ├─ pre-applypatch.sample
│  │  ├─ pre-commit.sample
│  │  ├─ pre-merge-commit.sample
│  │  ├─ pre-push.sample
│  │  ├─ pre-rebase.sample
│  │  ├─ pre-receive.sample
│  │  ├─ prepare-commit-msg.sample
│  │  ├─ push-to-checkout.sample
│  │  └─ update.sample
│  ├─ index
│  ├─ info
│  │  └─ exclude
│  ├─ logs
│  │  ├─ HEAD
│  │  └─ refs
│  │     ├─ heads
│  │     │  ├─ development
│  │     │  └─ main
│  │     └─ remotes
│  │        └─ origin
│  │           ├─ development
│  │           └─ main
│  ├─ objects
│  │  ├─ 01
│  │  │  ├─ 17b0ecce6451588c5fd27a95badc7bb48c693f
│  │  │  ├─ 1bbd618a1438dd2cf54d34615ed471fae4df13
│  │  │  ├─ 41dd34153909b54a4e7f32b52acac73941fcab
│  │  │  └─ c9b284e74a837cf9928a8c74e13d1f08e13ee9
│  │  ├─ 02
│  │  │  ├─ 44492f148d790b2a22ab63d89d9602af563d20
│  │  │  └─ d944267b80ee65666c1967d8448e8961967caf
│  │  ├─ 03
│  │  │  └─ 25e1709297fd58f008f7f664a459004978c9f6
│  │  ├─ 04
│  │  │  ├─ 117786458f9f0d7abfff9ef6220a196a9c58b6
│  │  │  ├─ 4f02586c1b6325235d8a166b4d7c4bd636388a
│  │  │  ├─ bfb97118531c60656b6c3743bcd42046d75751
│  │  │  └─ d7509173e3963a003a6ddc106d1c9b59468ecb
│  │  ├─ 05
│  │  │  └─ e86d1830e42987329bca0ff55063f1c505c4d9
│  │  ├─ 06
│  │  │  ├─ 6a89e8aba4ab85426ebfc25bd71dc73e1c3411
│  │  │  └─ a758698dfdbb3d9ac301127c15a5730a78979a
│  │  ├─ 07
│  │  │  ├─ d6d0efb02174cea184542a6b74d61002214525
│  │  │  └─ e2ba4a16ce85c900f0e215dd06f7c0037abfc4
│  │  ├─ 09
│  │  │  ├─ 8260b43f4bb325b919ef2722db01f7c46f8f9c
│  │  │  └─ ede0b66cb1bd2d49dcac12388a00dd7463cdec
│  │  ├─ 0a
│  │  │  └─ 81683594ab073fc178f49686a6cefab7bdec28
│  │  ├─ 0b
│  │  │  └─ 6181a47928b0f170971b93f68dc3d0085dacdf
│  │  ├─ 0c
│  │  │  ├─ 29aca6747112c3b6406b23558cc545e7a8808f
│  │  │  └─ ef36e0c84745801fe7416e3e06eb8db32395dd
│  │  ├─ 0d
│  │  │  └─ ee72e0f0ceeca3d63df6dfa8251d5d63906468
│  │  ├─ 0e
│  │  │  ├─ 0776faa11a1f3c303c394fd843f1d34479770c
│  │  │  ├─ 404951dba452623a056c7a0bb9ec1ffaf02d05
│  │  │  ├─ 75123c6438295388562aeb509ab8a38ed73798
│  │  │  ├─ 9071ad435df7abd03bf08cbae9657cd7b0798e
│  │  │  ├─ d4d76072a11e673e723d64c21e000e12f58d26
│  │  │  └─ ea3d3f8dab758b3c066fca57ffdd150264d4c7
│  │  ├─ 0f
│  │  │  └─ 443ba8e5f116dd523b46d872af64ea617b1f85
│  │  ├─ 10
│  │  │  └─ e25defd46a5c05165898b94ce15492003b5fac
│  │  ├─ 13
│  │  │  └─ 3e4862bef6baee6b53c10f0a73e714e6a40d36
│  │  ├─ 14
│  │  │  └─ d2d5b0986a7b04f9cb518e76ea1bf709cbd8c4
│  │  ├─ 15
│  │  │  └─ 720057809d1f7eff705bde68894e1c3a631c6f
│  │  ├─ 16
│  │  │  ├─ 85289896747b38bf4e6a481eee03599be543da
│  │  │  └─ 8a92186a8ef8773364cf70ee4635cd956d25c1
│  │  ├─ 18
│  │  │  ├─ 775cde13386da71f3bcbc3eac268adde0bc6af
│  │  │  └─ eb51cd151b041bf70153675cbea52b32104573
│  │  ├─ 1b
│  │  │  ├─ bd76a3757ae6b547600e609225e46bfed2f41a
│  │  │  └─ d97cad35354543f901006d673590cae034d10d
│  │  ├─ 1c
│  │  │  ├─ 6d9d45351ebcbd6c13e199d8297f883b149bf3
│  │  │  ├─ 8e5ac468c43d7e1ba57a57081c4b5d39145455
│  │  │  └─ e769eb7c865adb3124f78d108b6025fde6fc5f
│  │  ├─ 1d
│  │  │  └─ e77c0a5177ca06255a55bbd99a83ef0e637aa9
│  │  ├─ 1e
│  │  │  ├─ ebb16c5480270a46c2947c73b31465ad77e8fb
│  │  │  └─ f5247ff1c912377cb8c81de0ec36006679f430
│  │  ├─ 1f
│  │  │  ├─ 5fb3f91d9cf2a8db7eb3ef24eae4f0faae44bd
│  │  │  ├─ d54e749c28a8fc914a2f30f5dbbc664715d55c
│  │  │  └─ f40b9fbde1a94c6b261e6b0d47892b74296930
│  │  ├─ 20
│  │  │  ├─ 5a980d4e11e4c96c9922ee83bb30efdcfd3693
│  │  │  └─ b8df0fa225c696b4be025332c9c177ccc2f9b8
│  │  ├─ 21
│  │  │  ├─ 1b253dd2c3ecdf7f28e4f90c7c4930528f22b2
│  │  │  ├─ cd1287c0ef9d4b94715f14e622869229fbfe4a
│  │  │  └─ d968bca3e9a338466e37ef210f16d0551a27c2
│  │  ├─ 22
│  │  │  └─ 4f45893b9a8af2938d7505b95ad9619b87a5b0
│  │  ├─ 23
│  │  │  └─ 0cb2760a7ef295741112308887e10025db50bd
│  │  ├─ 24
│  │  │  ├─ 99732d7950326b5ebbeba1e40ba96d6ff15c95
│  │  │  ├─ c8318abec016438c4579f050c6e05e146cd6f3
│  │  │  └─ e32733a582174cdb6dc5924adfe0e3ac1652a2
│  │  ├─ 25
│  │  │  ├─ 3538cab6565b6600addc7c7b85df6e6f41ebb3
│  │  │  └─ dfcd529339c2b875995e5a11a92826b0d877a9
│  │  ├─ 28
│  │  │  └─ d0702265f6df99fca06e5b7bfbd9c22b74344e
│  │  ├─ 29
│  │  │  └─ 4e39c61f7d33612c0b5f937378022b97655b5f
│  │  ├─ 2a
│  │  │  └─ 39270174f6b5a4d9b07560c5f93e853bb40e66
│  │  ├─ 2b
│  │  │  └─ 3bc05d4451b77ddceeba7ce6aab39aab6a215f
│  │  ├─ 2c
│  │  │  ├─ 4defc77ceccf8853862c92efabd61191f3171f
│  │  │  └─ dd9f914da21ab8684dbd6f296a06fa22206d1b
│  │  ├─ 2d
│  │  │  └─ d215ac483943058a113474d0a5c7c91e512fe0
│  │  ├─ 2e
│  │  │  ├─ 115352deb8d5d4e771568a0e7ed951a717cc02
│  │  │  └─ f192b9685e1d3abe7c9d509dca7e9fb63c3505
│  │  ├─ 30
│  │  │  ├─ 3fd2b390de43213b6d965f609d898f1738a810
│  │  │  ├─ b2930e2c25b240b686d26537b3f63af3999013
│  │  │  └─ d7ea1ac9c5bbb79e4025cc85b3416638203de4
│  │  ├─ 31
│  │  │  └─ 4c30578dca1e2d0f5d28f825d64022b114e4ec
│  │  ├─ 32
│  │  │  ├─ ab2ab946b2850113073086f165eb6bda9dd2f3
│  │  │  └─ fae2b4501daadfbc4fe371bac717417253ea5b
│  │  ├─ 33
│  │  │  ├─ 5ca656169767c664c641af489d5d49b8a7e841
│  │  │  └─ ed068819da0a9f11c77e35f5825d10a4722bc0
│  │  ├─ 35
│  │  │  ├─ 65b02ef40783e67f3ed7f7496926b721333d03
│  │  │  └─ 8d83deda6fe11efa027758f05d579b22ec8423
│  │  ├─ 37
│  │  │  └─ 2c2fea87786e7fc6051a917cc7e3490101dcfb
│  │  ├─ 38
│  │  │  ├─ 8a8ab3ea3f1a37c23f62f07e8cba934896acd5
│  │  │  ├─ 8a8c43336154ce174fbb37e4ebb0fae50c0d6b
│  │  │  └─ b324fe2d6ef1a6ca62bcf52a66d7d6b994b46a
│  │  ├─ 39
│  │  │  └─ 1d70d5da111cdc2b6d79f6369c6ffc122b0ac3
│  │  ├─ 3b
│  │  │  ├─ 40bd3f3ccdcb9ae053f9973cdd8607f9cd3be7
│  │  │  ├─ 8e83306d5a5863696575decf553df9650bea7d
│  │  │  └─ a1b51bcf5f505f38b6a9b4fad486d01ba73c01
│  │  ├─ 3c
│  │  │  ├─ 5e7a404c84b8a9ebb94b85f6b3ccd76e8fc2fa
│  │  │  ├─ 8680afe25deedff5c34dd04b63245ac32a50e4
│  │  │  └─ f6435df47f581136c682d52ad04fbc3e9cb9f8
│  │  ├─ 3d
│  │  │  └─ 679cc29e8a55ae9a67a9dd99d7c1f8d3d52f30
│  │  ├─ 3e
│  │  │  └─ 77653dbed9afdc114651c1b52166e97a075bbc
│  │  ├─ 3f
│  │  │  ├─ 8c9aa0b02424676e57085e4fde1fd927d51721
│  │  │  └─ 98c09ae0b38f7f8ea03f7ba06aad8b9be1a064
│  │  ├─ 40
│  │  │  ├─ c96d5745a9d4ee23ba15b36c63625fe0ed1d9c
│  │  │  └─ e7db3854e04cb38d8799ab5b84f6f3873ebdbe
│  │  ├─ 41
│  │  │  ├─ 65a3840700d36286ffd37dffcfe1e8e3b1ba99
│  │  │  └─ bb33ba54f88d7187ccdba7d990114e0a27f197
│  │  ├─ 42
│  │  │  └─ c230bdc4df6285fcf7f05042cf1cde75be9196
│  │  ├─ 43
│  │  │  └─ d1e2cbc1029d60ac2a0e7914707a448f4d5f0e
│  │  ├─ 44
│  │  │  ├─ 722611cf9ad289c7407695d596bc69f546945d
│  │  │  ├─ 94faccaa8983f6d5db4df16faa0ece96523909
│  │  │  └─ 996274eb179f5e94ca0005752d32984f8b273e
│  │  ├─ 45
│  │  │  └─ 683f7f552a551ef46f465ca9630c25f0f9ba0f
│  │  ├─ 47
│  │  │  ├─ 00d2c9211ba749d77ebc6cfbdce10332ce28b7
│  │  │  └─ 3c3a62d0c66da3a82bc3c03d5260bc2b30e0e4
│  │  ├─ 49
│  │  │  └─ 43305ab2773450bbf0fa534bc34239acc7d5dd
│  │  ├─ 4a
│  │  │  ├─ bfb25abd298760c718dedafb0b5ea279b3157d
│  │  │  └─ cc4e68db0362fbd5b057066a7775090e676c19
│  │  ├─ 4b
│  │  │  └─ 93f8685565b27ea902a4e83880c707159efc90
│  │  ├─ 4c
│  │  │  └─ 68e47d0284e5c2c143c6bfcfec749718f445ce
│  │  ├─ 4d
│  │  │  ├─ ca7b8e681030aaf80609ede0571a89da3912c6
│  │  │  └─ dc44b46cec35894b4f331b00c113427d52b977
│  │  ├─ 4f
│  │  │  └─ 40b8fb778e6285683e3dc7ffe763f9f9ea1531
│  │  ├─ 50
│  │  │  └─ be79d2c83a6216de81ead927269f18304d5e3b
│  │  ├─ 51
│  │  │  ├─ a4b619065812d084e3e65732bd81dd06a97f2b
│  │  │  ├─ b67d1dfa17ef42432230c9c210b6afdfa429fb
│  │  │  ├─ d45f9a94c2cffe00a72eef9d25daaa56c7b01e
│  │  │  └─ eefa8c0b3599713929b604f9fb9ea84353be6b
│  │  ├─ 52
│  │  │  ├─ 243d35ae152a9d2e55e60392f4d297f022f1c9
│  │  │  ├─ 3b64f266a928ce1af8ee33d3246f7739c63db4
│  │  │  └─ ef3a1008eda7cd31c74da61709a0b42fa0636b
│  │  ├─ 53
│  │  │  ├─ 8c8338ebe54e8620b7e99cec42b5dec33fb7cf
│  │  │  ├─ c36f498c182f738458b10c62984f7401dc5a3b
│  │  │  └─ f6cae82ae83858db225980b7206d77db166c08
│  │  ├─ 57
│  │  │  ├─ 33c4cfe9b4ede419df3c92e3262130916d4d72
│  │  │  ├─ 6f9df57c72400b8cebf1978df15e2642cf6564
│  │  │  ├─ eb3ffd8680ae52c2631cadf0a2394c28b547a7
│  │  │  └─ f4fa5798a88988b87620dcc3b684fcef64e5c0
│  │  ├─ 59
│  │  │  ├─ 36341f33f8818f26e04e52a0095a09601608e8
│  │  │  ├─ 8ca512265fb204053633b3e2fdaf197265195b
│  │  │  └─ e0035208c7a20259c8090c6bba029d88d0183c
│  │  ├─ 5a
│  │  │  ├─ 166ce78e6b1484365f4b6878c1bd6012195134
│  │  │  ├─ ab0ca72394917cbe02734f2e715312785eb651
│  │  │  └─ cf25b380f8586490348b43d96080372c038dd4
│  │  ├─ 5b
│  │  │  ├─ 12429e1193dec0c175deaf82c5da767461a562
│  │  │  ├─ b9016316359642311727921762f352534f766a
│  │  │  ├─ d3872b4260876f0cb232ec1728ea9bc9ef0b3c
│  │  │  └─ f3a411c03b2db16476435b3341b8f9e6153665
│  │  ├─ 5c
│  │  │  └─ cf88242a9a811fc2778ea7ffe7c8b698b8a0f1
│  │  ├─ 5d
│  │  │  ├─ e7d9387232e725519ee811b9bf78f7b096cbfd
│  │  │  └─ f7f650b80fef281199e7d1c6bf53c6a5280802
│  │  ├─ 5e
│  │  │  ├─ 3dcf88693404fcf2765ffa01c8fe54d460c0a8
│  │  │  └─ 7867a03904dd8c6119a5e5246fc99cd8c62850
│  │  ├─ 60
│  │  │  ├─ 1b49a6078d3fdaf08e726a413494a61fe69225
│  │  │  └─ 1c44b8df46208346ed53eb46d59e590d61a28e
│  │  ├─ 62
│  │  │  ├─ 74a80726b44f69ca8e6d6e9aad9a30b541a239
│  │  │  └─ 912fa891442f61c83e40d1a2a2f2494e30dff4
│  │  ├─ 66
│  │  │  └─ d0d04472fc1f8e96999b5527213ac93075bad1
│  │  ├─ 67
│  │  │  ├─ 07e3ec1a9e9763fe0a4c43ab4723b89a442f9e
│  │  │  └─ 7a294680e3ea5063d52adb464eeb14235abb15
│  │  ├─ 68
│  │  │  └─ 8359a9300357ec2d6f2d2e32cc1c877a1dc821
│  │  ├─ 69
│  │  │  ├─ 38de1fd2681ec4dded793c35e543a8ec16b116
│  │  │  ├─ e16044f8b6fd8ae2a5b5401b161b2eb21dc837
│  │  │  └─ e48abfb3928e8586151b639977867ccc4b638a
│  │  ├─ 6a
│  │  │  ├─ 200278efa59f4fca18a8accc41f0754b19200c
│  │  │  └─ 87a6889f4e82ea90fd1a1702bccbe51781667e
│  │  ├─ 6b
│  │  │  ├─ 125f9619cbd78e501277268f72d52a8ad54335
│  │  │  ├─ 30bf9c679fcc7f0ea2e0d05fc4dbcc129971af
│  │  │  ├─ 58a02031e4d22c34811fa794ddc754a4512546
│  │  │  ├─ 7d67bcdb1b6aee1bd39898da2b849677721a39
│  │  │  └─ d3b81466ed255a723ff270ff4d995552910bac
│  │  ├─ 6c
│  │  │  └─ 66fe8c6c069ecf1e6b3d48d0666334220e709f
│  │  ├─ 6f
│  │  │  └─ 471fb5e4c7edfdb303b705c2c18d03fe267d78
│  │  ├─ 70
│  │  │  ├─ a2296b57b0e6ec5135702e38abc7ea60e459d8
│  │  │  └─ f7fe477acd86bfc4f26c62909431e40f92ddd2
│  │  ├─ 71
│  │  │  └─ 9d5ca05c058f675c2fd03028a2f14950078101
│  │  ├─ 72
│  │  │  └─ 16afbdc54837a8f5502f2c9ff971e6fa01997a
│  │  ├─ 73
│  │  │  ├─ 29c461d17ac1f7e0bd09dc1d58c25b264ab2c4
│  │  │  ├─ f0443d5a526e5484cc264eb601197b73569a3c
│  │  │  └─ fe8dcb422b4e38d7f641168354b34a801f7264
│  │  ├─ 74
│  │  │  └─ 7b4e59645a7561ec2c76794bd0be2a128cfa29
│  │  ├─ 75
│  │  │  ├─ 0e8c148d63c8ac6ce15e8a20051a5c59b00ade
│  │  │  ├─ 74e6ae1fccbc69aea6d7f52813b45557b96e03
│  │  │  └─ af3b01c7fccbac603d47305271be1ebdbc48fc
│  │  ├─ 76
│  │  │  └─ 5899ff029d47a1cb42a4ca627fa79138e2b1af
│  │  ├─ 77
│  │  │  └─ 18ba26036c148d8b77b1a667e68e96cca5f2ff
│  │  ├─ 7a
│  │  │  ├─ 04322d12beb385b5340b575e7ea3f444ae52c5
│  │  │  ├─ 42dfed3b9261d25d778c1196e3699ead516a42
│  │  │  ├─ 6ea47daefbbdad717824271c63f169b754137f
│  │  │  ├─ 7a861aef11eced275e69653f0f8cc69459dbc1
│  │  │  └─ de41eb83605dc2575308bbc2cc5578c1707c87
│  │  ├─ 7b
│  │  │  ├─ 93f96fde205e0187d2a352ab2f4253a1db2906
│  │  │  └─ e85dbe8df0c2a76a86e34ebe541f666153ab7a
│  │  ├─ 7c
│  │  │  ├─ 79d3e5ef3f3ecbe5d250106b3aa5b6170ded78
│  │  │  └─ 88cf69e680d40b09e1606d9a57e32bbc349c6b
│  │  ├─ 7d
│  │  │  └─ 833f31b0db93f2fb1287e5179fab8686761fbf
│  │  ├─ 7e
│  │  │  ├─ 170229efc529753b7386ca19f3d80b7b8d803d
│  │  │  ├─ 4d0830eb616d391e4414081624f163e7d4befb
│  │  │  ├─ 8828145e0c550e0d9c3c585350430c7f1da59f
│  │  │  └─ fef71df05c3d529cad519be99b183e110b0547
│  │  ├─ 80
│  │  │  └─ ae5c8bbeb11d1a4843d25b4de672d7ed408d9f
│  │  ├─ 82
│  │  │  └─ 837fddfbeed9cdde0f3c7133b585d98b3fa28b
│  │  ├─ 83
│  │  │  ├─ 02f1f46899e4830a8f38a29d828e07cf409f1a
│  │  │  ├─ 3807ea9bf28b8b6892bc5be7aea130aa44c9b3
│  │  │  └─ ad6995510832bba236da63245f10194ebb9238
│  │  ├─ 84
│  │  │  └─ 445487779d7565d885d48c182d6f6b91816a62
│  │  ├─ 86
│  │  │  ├─ 40c07ba194fa6a92642d121bab316378eaa651
│  │  │  ├─ 8039712e676cff52af74915d0e9ae03a4be86d
│  │  │  └─ 92b927ce26470cfd42c8ffa40d57a9c206c7f3
│  │  ├─ 87
│  │  │  └─ 386311410ef919c8b7e91d2883aab92c18077a
│  │  ├─ 88
│  │  │  ├─ 7a68684693443421b03ca671c1dd0211c3fe38
│  │  │  └─ a889ee28974966c10ffa36b93d4346cd89cd9a
│  │  ├─ 89
│  │  │  ├─ 5559c836d1bee4f9f1627b7ae04433fa34ed79
│  │  │  └─ eea25a909ec9f738dcb9ae53b7d1ae2f44efac
│  │  ├─ 8a
│  │  │  ├─ 21631502f185ddd216ec1ce9cadef6d33cb10b
│  │  │  └─ ac9a4c8b17db881f28483cf7a55338ec40c079
│  │  ├─ 8b
│  │  │  ├─ 137891791fe96927ad78e64b0aad7bded08bdc
│  │  │  ├─ 48f4624889a0f41ddca483eb961483203fe31c
│  │  │  └─ 89076691537dccd34405fba43be5364bcd021a
│  │  ├─ 8c
│  │  │  └─ d76dd55168920099634761938661968f7cc9a2
│  │  ├─ 8e
│  │  │  ├─ 2088c23fa6e71710009df33156f0e59a0790ad
│  │  │  └─ 468d55172cedae353fe363260cc9d1b14bb092
│  │  ├─ 90
│  │  │  ├─ 4bf8db8463ee3f29bc9c01c9031792c6d67cd1
│  │  │  └─ 839ddbd507f4fc468e1c0682c1b1370b582474
│  │  ├─ 91
│  │  │  └─ c5b5e819003d89d9063a7fb2f14ea6138559d3
│  │  ├─ 93
│  │  │  ├─ c235c444fa007fd58a93a1bc46543a70c71a6d
│  │  │  └─ cd01b94fadc75690b7d4c8042cdf776daf7736
│  │  ├─ 95
│  │  │  ├─ 1e65cc11ca18d29506aa6c43c5255b1b58fe49
│  │  │  ├─ 6536de554af447292cf965ec13a0a27cb45163
│  │  │  └─ b467980b08085bed8f267a5b085301b2d53d3e
│  │  ├─ 96
│  │  │  ├─ 116f2ebce6b055a10092395f217ec624436f73
│  │  │  └─ e5938b0d223eee05f96415f76e0476fc773944
│  │  ├─ 97
│  │  │  ├─ 203f17a8a64920a90b42c47e654f7daa0450b7
│  │  │  ├─ 48712ce9a20d98e59dec8102dfd0909bd30b61
│  │  │  ├─ 6c6df832982efe21b3b0921fef284ce78002e6
│  │  │  ├─ a47fc3dc2152085126dd7ed9cb7c8e5eff2658
│  │  │  └─ e0ea18f02a0c37b83b5cde8e078d7fd3d7f10a
│  │  ├─ 98
│  │  │  └─ 6a95ce37762b7207ca5fe74237cc565de264b4
│  │  ├─ 9b
│  │  │  ├─ 9999ec9a61012e3745650784c03ffff5886615
│  │  │  └─ bdf43923dd1005b7d412478e47ca13de1e4cd3
│  │  ├─ 9c
│  │  │  └─ 35e7e8ce9fc9ff9955261f842d65ac9918aded
│  │  ├─ 9e
│  │  │  ├─ 2299ce87921f64807a8f99671aee5206a5f00a
│  │  │  ├─ b9825f3ea771c814649b2a356a84b584b609b0
│  │  │  └─ fcac4d2622fbbcf6e1e0e1b9400e0948c9139d
│  │  ├─ 9f
│  │  │  └─ e191e5549ec0761e873d658824aeb40d4d2e63
│  │  ├─ a1
│  │  │  ├─ 8453e593117ebf7b1ebe1a4d863ad28727e377
│  │  │  └─ bedee186ee41b7676b04df38b06414db89b6c5
│  │  ├─ a2
│  │  │  └─ fe78fe19bc12de8da96ef304ba7506582a0501
│  │  ├─ a3
│  │  │  ├─ 3ef29b8d8a33c39536253ac4fc00e6ee7f130a
│  │  │  ├─ 5f25f59574c19c93f8fe83988e35703989c5a7
│  │  │  └─ eba63a34cd3d16e3078cb20cab3d18665691eb
│  │  ├─ a4
│  │  │  ├─ 20f2013d82b35db97325d7d316a3c58173b32b
│  │  │  └─ 4df59949066cb34ccfc32d238f68fc7e0e4da1
│  │  ├─ a5
│  │  │  └─ a062cdc6ae9fce6ddd571827255df0eabf9318
│  │  ├─ a6
│  │  │  ├─ a5a29e3b619095fa39d72b5b62a964fb110ac9
│  │  │  └─ d43cfbc405a7b36f9c1b707b0425a1e1a14b5b
│  │  ├─ a7
│  │  │  ├─ 56e2ae1a26a518ddb961c743ebb90d9457e2ac
│  │  │  ├─ 5dc17c9ee249bf36bc71889a7e425d7d774446
│  │  │  └─ 9a51a7bb573ad6c67067238676d8b4e84fffc8
│  │  ├─ a9
│  │  │  ├─ 06b347fee6fecedea5ac057a8b2e078ce2abad
│  │  │  ├─ 3c44bb1b5baa208a10ab6b354706d4ded01843
│  │  │  └─ 8a2d445660a9e07cc852b27b8a1881ebd3b614
│  │  ├─ aa
│  │  │  ├─ 02e7b84dd6f5fc0cec68bc80f73227a3f991c0
│  │  │  ├─ cd3f33a3c2fc97bd5fcdcc23a1f754af2a7253
│  │  │  ├─ d707039d4166e2c1a2548d5be76a0de0c06b26
│  │  │  ├─ e0664164ca5428bdfcd9fc5c9b24a00af849d0
│  │  │  └─ e7928deebf6fdc3efc989c10f863a1db79aa9c
│  │  ├─ ab
│  │  │  └─ d67f6a0508a7de9f564d66b1e5f7c808437ea1
│  │  ├─ ac
│  │  │  └─ 726394a015e77924776b3ab266da7c9d635d2d
│  │  ├─ ad
│  │  │  ├─ 8e80ccd1e8bb79fda494c1684c6bc55f5570c2
│  │  │  └─ fc79cbd39a614786f3c55b7cb83febb8a88ff5
│  │  ├─ ae
│  │  │  ├─ 6cc8f6d0c1097a50f5a1fbb01cb79484d8a224
│  │  │  ├─ 78bc90e60e1f7ed2fcbfda358b9a8c365fdbc3
│  │  │  └─ f94188f10df50a6922f0bfadcb9cc61d15d7b4
│  │  ├─ b0
│  │  │  ├─ 34bbe248c3ce6a223a9f8f9167255b0b22ccfa
│  │  │  ├─ 8b8786d3bfc3fa4b82e9bd917a2a6c8ce1e2e6
│  │  │  ├─ b5cdec7a7da013613ce37eaf248260dd41d592
│  │  │  └─ bee7776db1dc572a63dcc450ead9cbe1e164cb
│  │  ├─ b1
│  │  │  └─ ef13f376d7301b4653e469bd215c7e6e68845a
│  │  ├─ b2
│  │  │  ├─ 2260f9ba67247842636145088e53f973d79a01
│  │  │  ├─ 691c449d34e701d071d1cde76ebf0498474e91
│  │  │  ├─ bda5426a78586e15effe7ce0d793872f127790
│  │  │  └─ dd141fcaf347bd9cac652aa7bfe282d053d6b1
│  │  ├─ b3
│  │  │  ├─ 30b40dc7c23128fb56f4599caf6e2df4a1bdce
│  │  │  └─ 60a363e3d91fe3ce8dfa718e5482388fbe130d
│  │  ├─ b4
│  │  │  └─ 7cbb3dc3bca72e61fc311a235289eb93e6100d
│  │  ├─ b5
│  │  │  └─ ce1a97adc4fee32b4c477f9d9b3f305ede93e7
│  │  ├─ b6
│  │  │  └─ a3ec52032e002b6fee9dbcdc54603efb27ecb9
│  │  ├─ ba
│  │  │  └─ 9103d058e27128fae720213b3bbc2a36feb1f3
│  │  ├─ bb
│  │  │  ├─ d52890f5d1a60c365dd56b81025a618a41f379
│  │  │  └─ f9b91bfdffba3206ce136dca665af2cc9a8feb
│  │  ├─ bc
│  │  │  ├─ 830e30529901165dc3b71c1ae082d564b7568d
│  │  │  └─ 99271afa7b3c655a5782b7d524b23b1114056d
│  │  ├─ bd
│  │  │  ├─ 6a48f389ea94cc3fcefc29a9230ef2cff592a1
│  │  │  ├─ 75c94f5c9af812225bd9f2d40ce117c7fca45c
│  │  │  ├─ b0eb741d48aac44d2581f0d87e583ed9ce564f
│  │  │  └─ d5312dc19346c429e39ea05da2b532ac216470
│  │  ├─ c0
│  │  │  ├─ 32d53354cfb9821eb12589d7496f586b4d8dde
│  │  │  ├─ 83f39e02e94608f480971a00d7cbdd08ec03cd
│  │  │  └─ 8e48fba84d74500ba0ca26976652cd4a32d3eb
│  │  ├─ c1
│  │  │  ├─ 38933aed645584a841bdd767f6d4af882b1046
│  │  │  └─ ff362491b92d2c365c519025e7c25a7e92f349
│  │  ├─ c2
│  │  │  └─ a52e48170f48d827571c046a75ccdc861bb378
│  │  ├─ c3
│  │  │  └─ cb81e24db5d812381eb288b9be5d4141bba089
│  │  ├─ c4
│  │  │  ├─ 9a8c477340537a9b3487ef131cf175f5d46c01
│  │  │  └─ a19d94e39da7f072f51d89da0e0edce858ee2a
│  │  ├─ c6
│  │  │  └─ 32f99c7ef2d6980cc1b8a25dc1050a38457758
│  │  ├─ c8
│  │  │  └─ 48846a32d8e0a07d9709962dff03257d70b531
│  │  ├─ c9
│  │  │  ├─ 9460ae49124490cc051eda016d2fb6e671775d
│  │  │  └─ c8ce36745f2d41e4325358e0e81b169da87aab
│  │  ├─ ca
│  │  │  ├─ 7d06b2f957d668600ae5ee653180028234343a
│  │  │  └─ fe9e6b5f254cf4e33aa62a822612a318679b54
│  │  ├─ cb
│  │  │  ├─ 2096ca545674150ed0698db26f056abdb41f93
│  │  │  └─ d4da093e1ea8973650f207a14534c4b2d84e3f
│  │  ├─ cc
│  │  │  └─ cc43490c5eb89ff161259ce7523cc38209c030
│  │  ├─ cf
│  │  │  ├─ a5d5eb0d09c5870d643d4436a8b228ec3961f4
│  │  │  └─ f28224e03baa550a06fd5e835acee999345603
│  │  ├─ d0
│  │  │  └─ 88d0f24eb7675cc8f9ed7d7ea05133e240054c
│  │  ├─ d2
│  │  │  └─ ddf9e8c2736606e42406653d71fba9c16276c8
│  │  ├─ d3
│  │  │  └─ 19263c1d3d5f843e9ba5b1920e5bc053bf3584
│  │  ├─ d4
│  │  │  └─ 32481d50f570779e035f4ec0189d551e416ddd
│  │  ├─ d5
│  │  │  ├─ 0ceaeaf1ae42799e218a43cd0c58314f53809c
│  │  │  ├─ 48443538dd67566995b979eda5c2f103380c8e
│  │  │  └─ 97df96894f671eb1941549fd1f7dd8e9fd4a3c
│  │  ├─ d7
│  │  │  └─ b397bba032d2a3f425a074e03d5a3f8a63149c
│  │  ├─ d8
│  │  │  └─ eb503ecb3df34e375611dfcf26ce0afec4eb0f
│  │  ├─ da
│  │  │  └─ c5fa084aef1ae02403a0a86f54b0210d06e804
│  │  ├─ db
│  │  │  ├─ 2fb1e0d1780d6fa5f19313989b735be7629688
│  │  │  └─ 958b6df96003c12b0d5a83ff4faed0c59f048d
│  │  ├─ dc
│  │  │  └─ 32d9d140097aae1a86a718aadd881de526a3f0
│  │  ├─ dd
│  │  │  ├─ 967b9099696cb234cf0a7dd00ad2ce48068a02
│  │  │  └─ ea380e17ca9f3e08d1a3b4fc65ea2dc6efd629
│  │  ├─ df
│  │  │  └─ 321c6e01b49cfba46fd5d81fcc2e28d6e5131b
│  │  ├─ e2
│  │  │  └─ f0218e223c389f9575b42079b6c7533e613e22
│  │  ├─ e3
│  │  │  ├─ 293c45dffffca9216eaab3a0a2161c2000a143
│  │  │  ├─ 4ae03b8712120c924fc3118deccec99921dc3f
│  │  │  └─ df3d4af206e87e8272cb7ba874d24caf5cbbf0
│  │  ├─ e4
│  │  │  ├─ 35e01bcfcf4b923a0ad9460eab77acc53fc86a
│  │  │  └─ d47387f067ad0791aebe80bcf7fcc1e6606e96
│  │  ├─ e5
│  │  │  └─ 8a92b0b42fc0d383c4c14afddb7f817e50aaa1
│  │  ├─ e6
│  │  │  ├─ 721fd94aa28ef156891cea1d9bc4ea2a0a3df0
│  │  │  └─ 9de29bb2d1d6434b8b29ae775ad8c2e48c5391
│  │  ├─ e7
│  │  │  ├─ 1acf0bb1054bad0f5db6126a6533220097f9e9
│  │  │  ├─ 8027e1903779636a50f036bb7c23b802ee60a2
│  │  │  ├─ 988434cf9fa3a4baeb76a1f6b75ca1855eb0b4
│  │  │  └─ be930eb1c2d007b54708234ce8aa6f0e76f5df
│  │  ├─ e8
│  │  │  └─ 15fffec1322ff465d79b4c22d18046d9c93465
│  │  ├─ e9
│  │  │  └─ 7c6446c87e3bc8b140213be2e8cc1b5e4bd217
│  │  ├─ ea
│  │  │  ├─ c8f813f258bbbeed45214c46463616775adc87
│  │  │  └─ ce5b0310502441f95b60cb5b4119db8cd22458
│  │  ├─ eb
│  │  │  └─ f21542bde192e1960c63aa27d58da2d1357bbb
│  │  ├─ ec
│  │  │  ├─ 8d1385ee058ba048fe58387e48f957fbfd76a6
│  │  │  └─ d0ff989f19e1caeecf53a4de429d6890b81f75
│  │  ├─ ed
│  │  │  └─ d6bd48bf10d9627ca47b1c9fa2406af943957f
│  │  ├─ ef
│  │  │  ├─ 46847b646964eac00e5aa6aa58901ebbcae626
│  │  │  └─ fd3f5c69c1de6ab2d05c40afb57712ebdbb3fb
│  │  ├─ f0
│  │  │  └─ 55bca09f57eb24a87ffdf30cdb013bd0725254
│  │  ├─ f1
│  │  │  ├─ ddc840ea8d63c0e83799a83fe2d8c69cf8469a
│  │  │  └─ e783b22b6addcc8701b15d81b95b0fd2753335
│  │  ├─ f3
│  │  │  ├─ 8d1eb03ac9d6dbeed0528ea6cd1454e87bd597
│  │  │  ├─ 97af0bf19c218ccea19b3d2936b10a9df221fe
│  │  │  ├─ bafb83d1047bc7ea90ad8d10f9d681ddac58bc
│  │  │  └─ fd96dd80bd8a791567b10ab8f9d1ce2730dcf6
│  │  ├─ f5
│  │  │  └─ 7ffcbcfc4404e8f81f8ac17af7bcff4527540e
│  │  ├─ f7
│  │  │  ├─ 0c70eedf948769cd6a641e492b2d143e78d2e0
│  │  │  └─ dda74cc87571e94f12b28751481004170709ea
│  │  ├─ f9
│  │  │  ├─ 9e9471617ec46e596d0ed0bc5690d75426845c
│  │  │  └─ c4a76ea1dba09c696c7cc0544a1c803b36598d
│  │  ├─ fa
│  │  │  └─ 8d8f5855ec6fc660f8b5cfb1b24777a1c737ca
│  │  ├─ fb
│  │  │  └─ bc1eda9abeaab17c377c42cc298d27a90ef7d9
│  │  ├─ fc
│  │  │  ├─ 7787299fa95b481e28fba5aa2d5bf81f61316a
│  │  │  └─ edc8ab499a8b30178167efa5a814c840a946e2
│  │  ├─ fd
│  │  │  └─ c6b8877489f784d31eb397d4867e8587e3c76a
│  │  ├─ fe
│  │  │  └─ 31549008c6808371d2c2435d8c8e7d706d4e75
│  │  ├─ ff
│  │  │  └─ 7239677cd56b903f267ab00a0e9d4f9487e233
│  │  ├─ info
│  │  └─ pack
│  │     ├─ pack-a3b315c20e464919692d4cf9237d2ce52fbf4b6b.idx
│  │     └─ pack-a3b315c20e464919692d4cf9237d2ce52fbf4b6b.pack
│  ├─ ORIG_HEAD
│  ├─ packed-refs
│  └─ refs
│     ├─ heads
│     │  ├─ development
│     │  └─ main
│     ├─ remotes
│     │  └─ origin
│     │     ├─ development
│     │     └─ main
│     └─ tags
│        └─ M2
├─ .github
│  └─ workflows
│     ├─ main.yml
│     └─ pr.yml
├─ .gitignore
├─ app.yaml
├─ gradle
│  └─ wrapper
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ LICENSE
├─ README.md
├─ settings.gradle
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ ch
│  │  │     └─ uzh
│  │  │        └─ ifi
│  │  │           └─ hase
│  │  │              └─ soprafs24
│  │  │                 ├─ Application.java
│  │  │                 ├─ constant
│  │  │                 │  └─ UserStatus.java
│  │  │                 ├─ controller
│  │  │                 │  ├─ GameController.java
│  │  │                 │  ├─ GameWebSocketController.java
│  │  │                 │  ├─ SpotifyController.java
│  │  │                 │  └─ UserController.java
│  │  │                 ├─ entity
│  │  │                 │  ├─ Game.java
│  │  │                 │  ├─ Player.java
│  │  │                 │  ├─ Settings.java
│  │  │                 │  ├─ SongInfo.java
│  │  │                 │  └─ User.java
│  │  │                 ├─ exceptions
│  │  │                 │  └─ GlobalExceptionAdvice.java
│  │  │                 ├─ repository
│  │  │                 │  ├─ GameRepository.java
│  │  │                 │  ├─ PlayerRepository.java
│  │  │                 │  ├─ SongInfoRepository,java
│  │  │                 │  └─ UserRepository.java
│  │  │                 ├─ rest
│  │  │                 │  ├─ dto
│  │  │                 │  │  ├─ GameGetDTO.java
│  │  │                 │  │  ├─ GamePostDTO.java
│  │  │                 │  │  ├─ UserGetDTO.java
│  │  │                 │  │  └─ UserPostDTO.java
│  │  │                 │  └─ mapper
│  │  │                 │     └─ DTOMapper.java
│  │  │                 ├─ service
│  │  │                 │  ├─ GameService.java
│  │  │                 │  ├─ SpotifyService.java
│  │  │                 │  └─ UserService.java
│  │  │                 └─ websocket
│  │  │                    ├─ dto
│  │  │                    │  ├─ GameResponseDTO.java
│  │  │                    │  ├─ JoinRoomPayloadDTO.java
│  │  │                    │  ├─ LeaveRoomPayloadDTO.java
│  │  │                    │  ├─ ModifySettingsPayloadDTO.java
│  │  │                    │  ├─ PlayerSongInfoDTO.java
│  │  │                    │  └─ SendEmojisPayloadDTO.java
│  │  │                    ├─ mapper
│  │  │                    │  └─ DTOMapper.java
│  │  │                    └─ WebSocketConfiguration.java
│  │  └─ resources
│  │     ├─ application.properties
│  │     └─ META-INF
│  │        └─ additional-spring-configuration-metadata.json
│  └─ test
│     └─ java
│        └─ ch
│           └─ uzh
│              └─ ifi
│                 └─ hase
│                    └─ soprafs24
│                       ├─ controller
│                       │  ├─ GameControllerTest.java
│                       │  ├─ SpotifyControllerTest.java
│                       │  └─ UserControllerTest.java
│                       ├─ repository
│                       │  ├─ GameRepositoryIntegrationTest.java
│                       │  ├─ PlayerRepositoryIntegrationTest.java
│                       │  ├─ SongRepositoryIntegrationTest.java
│                       │  └─ UserRepositoryIntegrationTest.java
│                       ├─ rest
│                       │  └─ mapper
│                       │     └─ DTOMapperTest.java
│                       ├─ service
│                       │  ├─ GameServiceTest.java
│                       │  ├─ UserServiceIntegrationTest.java
│                       │  └─ UserServiceTest.java
│                       └─ websocket
│                          └─ WebSocketConfigTest.java
└─ system.properties

```