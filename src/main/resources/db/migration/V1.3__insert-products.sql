CREATE SEQUENCE IF NOT EXISTS public.product_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE SEQUENCE IF NOT EXISTS public.file_upload_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE table IF NOT EXISTS public.product
(
    id                integer not null default nextval('public.product_id_seq'::regclass),
    create_date_time  timestamp,
    deleted_date_time timestamp,
    comment           varchar(1000),
    record_status     varchar(255),
    update_date_time  timestamp,
    version           bigint,
    name              varchar(255) unique,
    description       varchar(2000),
    unit_price        numeric(19, 2),
    brand_id          integer NOT NULL,
    platform_id       integer NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES public.brand (id),
    FOREIGN KEY (platform_id) REFERENCES public.platform (id),

    PRIMARY KEY (id)
);

create index if not exists name_idx
    on product (name);

CREATE table IF NOT EXISTS public.file_upload
(
    id                integer not null default nextval('public.product_id_seq'::regclass),
    create_date_time  timestamp,
    deleted_date_time timestamp,
    comment           varchar(1000),
    record_status     varchar(255),
    update_date_time  timestamp,
    version           bigint,
    file_extension    varchar(255),
    file_name         varchar(255),
    file_path         varchar(1000),
    product_id        integer NOT NULL,
    FOREIGN KEY (product_id) REFERENCES public.product (id),

    PRIMARY KEY (id)
);

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed',
        'Relive the American Revolution or experience it for the first time in Assassins Creed III Remastered, with enhanced graphics and improved gameplay mechanics. Plus, Assassins Creed Liberation Remastered and all solo DLC content are included.
        1775. The American Colonies are about to revolt. As Connor, a Native American Assassin, secure liberty for your people and your nation. From bustling city streets to the chaotic battlefields, assassinate your foes in a variety of deadly ways with a vast array of weaponry.
        All the original solo DLC, including The Tyranny of King Washington, and the entire game Assassins Creed Liberation Remastered are included in the game.',
        56.5, 1, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'ac4-cover.jpg',
        'D:\springboot-training/upload/ac4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'MORTAL KOMBAT', 'Mortal Kombat™ XL is NetherRealm Studios’ next highly anticipated instalment in its legendary, critically acclaimed fighting game franchise that propels the iconic franchise into a new generation. Following on from the global success of Mortal Kombat™ X, the game combines all the excitement and action from Mortal Kombat™ X with brand new content that isn’t available in the standard edition, allowing for more gruelling action and vicious fatalities.
For the first time, Mortal Kombat™ XL introduces 4 new DLC characters into the mix along with a brand new environment, the Pit. Enjoy next generation fatalities in high definition and step into an epic original story mode that pushes Mortal Kombat™ 25 years into the future! ' ||
                                                                                                 'NEXT-GEN FATALITIES:
Mortal Kombat™ XL offers you the chance to crush your opponent with the most intense and gruesome finishing moves ever.
EVERY CHARACTER & VARIATION:
For the first time, play from the entire MKX roster – completely unlocked! You can play as the most iconic movie characters and brand-new fan favourites. Customise your fights like never before!
CINEMATIC STORY MODE:
Step into a brand-new story mode that pushes Mortal Kombat™ 25 years into the future.', 49.5, 2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mortal-kombat.jpg',
        'D:\springboot-training/upload/mortal-kombat.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield 4',
        'Embrace unrivaled destruction in Battlefield 4™. Revel in the glorious chaos of all-out war packed with rewarding, tactical challenges in an interactive environment. Demolish the buildings shielding your enemy and lead an assault from the back of a gun boat. You have the freedom to do more and be more, playing to your strengths and carving your own path to victory. There is no comparison. Immerse yourself in the glorious chaos of all-out war',
        35, 3, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE',
        null, 0, 'jpg', 'bf4-cover.jpg',
        'D:\springboot-training/upload/bf4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Hitman 2',
        'Travel the globe and track your targets across exotic sandbox locations in HITMAN™ 2. From sun-drenched streets to dark and dangerous rainforests, nowhere is safe from the world’s most creative assassin, Agent 47 in the ultimate spy thriller story. Prepare to experience the ultimate spy thriller story; your mission is to eliminate the elusive Shadow Client and unravel his militia, but when 47 learns his target''s true identity and the truth about his past, it changes everything. HITMAN™ 2 introduces new ways to play, new game modes and new features, including the new Sniper Assassin mode with franchise first co-op play.',
        9.99, 4, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'hitman.jpg',
        'D:\springboot-training/upload/hitman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'GTA V',
        'Grand Theft Auto V is set circa 2013 in the city of Los Santos and its surrounding areas and tells the stories of three protagonists: Michael De Santa, Franklin Clinton, and Trevor Philips. It takes place about five years after the events of Grand Theft Auto IV, and four years after the events of Grand Theft Auto: Chinatown Wars.Developed by series creator Rockstar North, Grand Theft Auto V heads to the city of Los Santos and its surrounding hills, countryside and beaches in the largest and most ambitious game Rockstar has yet created - Rockstar',
        45.99, 5, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'gtav.jpg',
        'D:\springboot-training/upload/gtav.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'DIRT', 'Dirt Rally is a racing video game focused on rallying. Players compete in timed stage events on tarmac and off-road terrain in varying weather conditions. On release, the game featured 17 cars, 36 stages from three real world locations - Monte Carlo, Powys and Argolis - and asynchronous multiplayer.
', 35, 6, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'dirt.jpg',
        'D:\springboot-training/upload/dirt.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Black Ops 2', 'Pushing the boundaries of what fans have come to expect from the record-setting entertainment franchise, Call of Duty: Black Ops II propels players into a near future, 21st Century Cold War, where technology and weapons have converged to create a new generation of warfare.Rooted in near-future fiction, Call of Duty®: Black Ops 2 multiplayer will introduce players to some of the most cutting-edge weaponry and equipment that 2025 will have to offer
', 7.5, 7, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'cod-blackops2.jpg',
        'D:\springboot-training/upload/cod-blackops2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Modern Warfare 3', 'Call of Duty: Modern Warfare 3 is the eighth main Call of Duty game, and the fifth developed by Infinity Ward. It''s the sequel to Call of Duty: Modern Warfare 2 and is the third installment in the Modern Warfare series until 2019.
The Russian invasion of the United States continues, culminating in the Battle of Lower Manhattan. A Delta Force team with the call sign “Metal” under the command of Sandman is deployed in New York City to repel the Russian assault along the East River. Team Metal succeeds in destroying a jamming device mounted on the rooftop of the New York Stock Exchange building and escapes on a Black Hawk helicopter. They later assist the U.S. Navy SEALs in boarding a Russian Oscar II to use its ordnance against the Russian Navy. As a result of Team Metal''s actions, the Russians withdraw from the East Coast, and presumably the entire United States.',
        12.5, 1, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'call_of_duty333.jpg',
        'D:\springboot-training/upload/call_of_duty333.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Spider-Man', 'Marvel''s Spider-Man[a] is a video game based on the Marvel Comics superhero Spider-Man, developed by Insomniac Games and published by Sony Interactive Entertainment. It was released worldwide on September 7, 2018 for the PlayStation 4, as the first licensed game developed by Insomniac.
The game follows Peter Parker, a 23-year-old college graduate and research assistant who gained superhuman abilities after being bitten by a radioactive spider. Peter is in his eighth year of crime-fighting under his super heroic alter ego of Spider-Man, who has become highly experienced at this point, but struggles to balance his superhero and personal lives.',
        65, 2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'spiderman.jpg',
        'D:\springboot-training/upload/spiderman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'PES 2019', 'PES 2019 LITE offers unrestricted access to myClub mode, that allows players to sign current footballing superstars and legends such as David Beckham, Diego Maradona and Gabriel Batistuta, as well as FC Barcelona''s Ronaldinho, Inter''s Recoba, Cambiasso, Djorkaeff and Adriano, with more to be added following launch.
New to myClub this year is Featured Players, whose abilities are influenced by their performance in the previous weekend''s fixtures. Available every week, these special players will have boosted stats based on their match performance, with some even receiving new skills.
Don''t miss this opportunity to build your own dream squad through PES''s flagship mode!', 22, 3, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'pes2019.jpg',
        'D:\springboot-training/upload/pes2019.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Super Mario Bros 3', 'A plumber named Mario and his brother Luigi travel through the Mushroom Kingdom to save the princess from the evil Bowser. Plumber brothers Mario and Luigi from Brooklyn have just arrived in an outlandish realm called the Mushroom Kingdom. It was ruled by Princess Toadstool and her faithful Mushroom people
', 32, 4, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mario.jpg',
        'D:\springboot-training/upload/mario.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Halo 4', 'After being awoken from cryogenic sleep, Master Chief crash lands on a planet infested by aliens known as Prometheans. Halo 4 is the start of a new saga in the Master Chief''s story. This saga is known as the Reclaimer Saga. At the end of the legendary mode of Halo 3 we see Master Chief Petty Officer John-117 orbiting a planet and hear him say to Cortana "Wake me when you need me". Well now is the time to be needed. The game starts where you last left off in a torn in half ship orbiting a planet around four years after the events of Halo 3. John wakes up to Cortana screaming "John I need you!" This creates the beginning of a new saga.
', 5, 5, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'halo4.jpg',
        'D:\springboot-training/upload/halo4.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Fifa 19', 'Led by the prestigious UEFA Champions League, FIFA 19 offers enhanced gameplay features that allow you to control the pitch in every moment, and provides new and unrivaled ways to play, including a dramatic finale to the story of Alex Hunter in The Journey: Champions*, a new mode in the ever-popular FIFA Ultimate Team™, and more. Champions Rise in FIFA 19
', 25, 6, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'fifa19.jpg',
        'D:\springboot-training/upload/fifa19.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield V', 'Battlefield V is a first-person shooter video game developed by EA DICE and published by Electronic Arts. Battlefield V is the sixteenth installment in the Battlefield series. It was released worldwide for Microsoft Windows, PlayStation 4, and Xbox One on November 20, 2018. Those who pre-ordered the Deluxe Edition of the game were granted early access to the game on November 15, 2018,[2] and Origin Access Premium subscribers on PC received access to the game on November 9, 2018.[3] The game is based on World War II and is a thematic continuation of its World War I based precursor Battlefield 1.
', 43, 7, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'bf5.jpg',
        'D:\springboot-training/upload/bf5.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Man of Medan', 'Man of Medan, like all the games in the Dark Pictures Anthology, is hugely replayable. In fact there is more branching in Man of Medan than any of our previous games. There are multiple endings and multiple scenarios based on the decisions you make in the game. Be careful though as all the playable characters can live, and any and all of them can die, depending on the choices you make. Through the protaganists eyes you will uncover the secrets of the mystery surrounding the ill-fated boat. Figuring this out can save their lives – but whether or not they all escape is up to you.
', 34, 1, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'man-of-medan.jpg',
        'D:\springboot-training/upload/man-of-medan.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Batman Arkham Knight', 'Batman: Arkham Knight is a 2015 action-adventure video game developed by Rocksteady Studios.Arkham Knight''s main story follows Scarecrow, the Arkham Knight, and Deathstroke, who have made an alliance on Halloween night, and plan to drown Gotham City in fear toxin and unmask Batman. The game has received universal acclaim but was criticized for the extensive use of the Batmobile combat segments. The PC port was released to widespread criticism over performance issues and technical glitches, eventually leading to the PC version being pulled from online retailers and digital distributors for months. It was even considered to be one of the worst PC ports of all time. On console, the game saw little to no issues upon release.
', 27, 2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'batman.jpg',
        'D:\springboot-training/upload/batman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Cyberpunk 2077', 'Cyberpunk 2077 is an open-world, action-adventure story set in Night City, a megalopolis
obsessed with power, glamour and body modification. You play as V, a mercenary outlaw going after a one-of-a-kind implant that is the key to immortality. You can customize your character’s cyberware, skillset and playstyle, and explore a vast city where the choices you make shape the story and the world around you.Become a cyberpunk, an urban mercenary equipped with cybernetic enhancements and build
your legend on the streets of Night City.Enter the massive open world of Night City, a place that sets new standards in terms of visuals, complexity and depth.Take the riskiest job of your life and go after a prototype implant that is the key to immortality.',
        67, 3, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'cyberpunk2077.jpg',
        'D:\springboot-training/upload/cyberpunk2077.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'GTA IV', 'What does the American dream mean today?
For Niko Bellic fresh off the boat from Europe, it is the hope he can escape from his past. For his cousin, Roman, it is the vision that together they can find fortune in Liberty City, gateway to the land of opportunity. As they slip into debt and get dragged into a criminal underworld by a series of shysters, thieves and sociopaths, they discover that the reality is very different from the dream in a city that worships money and status, and is heaven for those who have them and a living nightmare for those who don''t.
New high resolution technology brings Liberty City to life with even more stunning graphical detail
Expanded multiplayer will require even more strategy and skill to come out on top
Custom Match allows you to pick your favorite multiplayer options and then instantly find online matches that fit
Optimized for PC controls and Xbox 360 controller', 8, 4, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'gta-iv.jpg',
        'D:\springboot-training/upload/gta-iv.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Injustice 2', 'Power up and build the ultimate version of your favourite DC legends in INJUSTICE 2.
•EVERY BATTLE DEFINES YOU: With every match you''ll earn gear to equip, customise and evolve your roster.
•A NEW THREAT RISES: Picking up where Injustice left off, Batman struggles against Superman''s regime, as a new threat appears that will put Earth''s very existence at risk.
•THE BEST OF DC: Choose from the biggest DC Universe roster ever and battle across iconic locations in epic-scale battles.
•BUILT BY NETHERREALM: Developers of the best-selling and critically acclaimed MORTAL KOMBAT franchise.

Fighter Pack 1
• Access to 3 playable characters: Red Hood, Starfire, and Sub-Zero
• Gods Shader Pack
Fighter Pack 2
• Access to 3 playable characters: Black Manta, Raiden, and Hellboy
• Reverse-Flash Premiere Skin
Fighter Pack 3
• Access to 3 playable characters: Atom, Enchantress, and TMNT
• John Stewart Green Lantern Premiere', 53, 5, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'injustice.jpg',
        'D:\springboot-training/upload/injustice.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Wolfenstein',
        'Talking about Wolfenstein is never an easy task. After all, Wolfenstein 3D was a milestone in the computer gaming industry. According to many, the first FPS ever created (which is actually, a wrong statement. Wolfenstein 3D popularized the FPS genre but it wasn''t the first). Wolfenstein: The New Order on Steam is a resurrection of the series. You are going to get everything you would expect from Wolfenstein. A fast-paced action, interesting plot and Nazis to kill. Yeah, what else do we need? Maybe a glass of whiskey.',
        67, 6, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'wolfenstein.jpg',
        'D:\springboot-training/upload/wolfenstein.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Star Wars',
        'Meet Iden Ersio, the commander of an elite TIE Fighter squadron called the Inferno Squad, merging aerial superiority with commando tactics on the ground. The story begins when she sees the second Death Star explode, and things progress rapidly from there. Join her on a thirty-year journey across the galaxy far away, and see if she manages to complete the Emperor''s final order. Characters you know and love make their guest appearances, letting you play as Kylo Ren or Luke Skywalker in missions tailored to these characters. It''s an excellent singleplayer experience bridging the gap between Episodes VI and VII',
        51, 7, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'starwars.jpg',
        'D:\springboot-training/upload/starwars.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed Rogue',
        'North America, 18th century. Amid the chaos and violence of the French and Indian War, Shay Patrick Cormac, a fearless young member of the Assassin Brotherhood, undergoes a dark transformation that will forever shape the future of the colonies. After a dangerous mission goes tragically wrong, Shay turns his back on the Assassins who, in response, attempt to end his life. Cast aside by those he once called brothers, Shay sets out on a mission to wipe out all who turned against him and to ultimately become the most feared Assassin hunter in history. Introducing Assassin''s Creed Rogue, the darkest chapter in the Assassin''s Creed franchise yet. As Shay, you will experience the slow transformation from Assassin to Assassin hunter. Follow your own creed and set off on an extraordinary journey through New York City, the wild river valley, and far away to the icy cold waters of the North Atlantic in pursuit of your ultimate goal, to bring down the Assassins.',
        8, 2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'acrogue.jpg',
        'D:\springboot-training/upload/acrogue.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'The Witcher 3',
        'The world burns as the Empire of Nilfgaard pierces through the heart of the kingdoms in the north. Geralt of Rivia is pursued by an enemy devoid of mercy—the Wild Hunt, a mythical cavalcade of ghastly riders, harbingers of doom and destruction, capable of destroying entire settlements overnight. Driven by a deeply personal agenda, Geralt must navigate a maze of hostile forces to find and protect the one described in an ancient prophecy. Gritty and merciless, the world the adventure plays out in sets new standards in terms of size, ecosystem complexity and meaningful non-linearity. Experience realistic day and night cycles, observe weather changes that influence gameplay, and conquer the lush environment using arcane witcher lore.',
        30, 4, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'witcher.jpg',
        'D:\springboot-training/upload/witcher.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Mortal Kombat 9',
        'Experience the deadliest tournament with all the kombatants and their unique fatalities. Players enter the realm to face the Kombatants in Mortal Kombat Komplete Edition, delivering all of the downloadable content (DLC), including intrepid warriors Skarlet, Kenshi and Rain, as well as the notorious dream stalker Freddy Krueger. Additionally, the game offers 15 Klassic Mortal Kombat Skins and three Klassic Fatalities (Scorpion,Sub-Zero and Reptile). Mortal Kombat Komplete Edition features dynamic gameplay including Tag Team, Challenge Tower and a full feature length story mode. Players choose from an extensive lineup of the game’s iconic warriors and challenge their friends in traditional 1 vs. 1 matches, or gamers can spectate battles and interact directly with Kombatants online during the King of the Hill mode.',
        29, 3, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mk9.jpg',
        'D:\springboot-training/upload/mk9.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Deus Ex: Mankind Divided',
        'Deus Ex: Mankind Divided directly follows the aftermath of the Aug Incident, a day when mechanically augmented citizens all over the world were stripped of control over their minds and bodies, resulting in the deaths of millions of innocents. The year is now 2029, and the golden era of augmentations is over. Mechanically augmented humans have been deemed outcasts and segregated from the rest of society. Crime and acts of terror serve as a thin veil to cover up an overarching conspiracy aimed at controlling the future of mankind…',
        47.99,
        3, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'deus-ex.jpg',
        'D:\springboot-training/upload/deus-ex.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Resident Evil 2',
        'A deadly virus engulfs the residents of Raccoon City in September of 1998, plunging the city into chaos as flesh eating zombies roam the streets for survivors. An unparalleled adrenaline rush, gripping storyline, and unimaginable horrors await you. Witness the return of Resident Evil 2.',
        15.99, 2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'resident-evil-2.jpg',
        'D:\springboot-training/upload/resident-evil-2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Borderlands 3',
        'The original shooter-looter returns, packing bazillions of guns and an all-new mayhem-fueled adventure! Blast through new worlds and enemies as one of four brand new Vault Hunters – the ultimate treasure-seeking badasses of the Borderlands, each with deep skill trees, abilities, and customization. Play solo or join with friends to take on insane enemies, score loads of loot and save your home from the most ruthless cult leaders in the galaxy.Stop the fanatical Calypso Twins from uniting the bandit clans and claiming the galaxy’s ultimate power. Only you, a thrill-seeking Vault Hunter, have the arsenal and allies to take them down.Become one of four extraordinary Vault Hunters, each with unique abilities, playstyles, deep skill trees, and tons of personalization options. All Vault Hunters are capable of awesome mayhem alone, but together they are unstoppable.With bazillions of guns and gadgets, every fight is an opportunity to score new gear. Firearms with self-propelling bullet shields? Check. Rifles that spawn fire-spewing volcanoes? Obviously. Guns that grow legs and chase down enemies while hurling verbal insults? Yeah, got that too.Discover new worlds beyond Pandora, each featuring unique environments to explore and enemies to destroy. Tear through hostile deserts, battle your way across war-torn cityscapes, navigate deadly bayous, and more!Play with anyone at any time online or in split-screen co-op, regardless of your level or mission progress. Take down enemies and challenges as a team, but reap rewards that are yours alone – no one misses out on loot.With bazillions of guns and gadgets, every fight is an opportunity to score new gear. Firearms with self-propelling bullet shields? Check. Rifles that spawn fire-spewing volcanoes? Obviously. Guns that grow legs and chase down enemies while hurling verbal insults? Yeah, got that too.',
        49.99, 1, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'borderlands-3.jpg',
        'D:\springboot-training/upload/borderlands-3.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Dishonored 2', 'Reprise your role as a supernatural assassin in Dishonored 2.
Praised by PC Gamer as “brilliant”, IGN as “amazing” and “a super sequel, IGN as “amazing” and “a superb sequel”, declared a “masterpiece” by Eurogamer, and hailed “a must-play revenge tale among the best in its class” by Game Informer, Dishonored 2 is the follow up to Arkane Studio''s first-person action blockbuster and winner of more than 100 ''Game of the Year'' awards, Dishonored. Play your way in a world where mysticism and industry collide. Will you choose to play as Empress Emily Kaldwin or the royal protector, Corvo Attano? Will you make your way through the game unseen, make full use of its brutal combat system, or use a blend of both? How will you combine your character''s unique set of powers, weapons and gadgets to eliminate your enemies? The story responds to your choices, leading to intriguing outcomes, as you play through each of the game''s hand-crafted missions.
Dishonored 2 is set 15 years after the Lord Regent has been vanquished and the dreaded Rat Plague has passed into history. An otherworldly usurper has seized Empress Emily Kaldwin’s throne, leaving the fate of the Isles hanging in the balance. As Emily or Corvo, travel beyond the legendary streets of Dunwall to Karnaca, the once-dazzling coastal city that holds the keys to restoring Emily to power. Armed with the Mark of the Outsider and powerful new abilities, track down your enemies and take back what’s rightfully yours.',
        30.50, 6, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'dishonored2.jpg',
        'D:\springboot-training/upload/dishonored2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Just Cause 4',
        'Just Cause 4 (JC4) will once again show off the high doses of action that popularized the series, with a multitude of explosions, shootings, vehicle surfing, and for the first time, natural disasters. Explore the island divided into exactly four different climate zones. Visit mountainous areas, steppes, rain forests and even a hot desert. Each of the quoted zones presents a unique experience for the player and allows them to use the available game mechanics in a different way. Dangerous weather hazards are present in different locations. Watch out for events such as snow blizzards (especially in mountainous locations), thunderstorms and tornados. Find out, that each location has its own, unique elements. Encounter snowmobiles or railway lines as you travel through the snowy regions. Although the fictitious Solis located in South America is mainly occupied by wild, untamed areas, you can also explore some small towns and villages. Be extremely careful because you can bump into sandstorms on your way. Just Cause 4 features amazing, dynamic weather that is associated with individual geographical areas. Marvel at perfectly designed, beautiful locations and delight with excellent graphics performance during your adventure. Take advantage of your various tools and weapons and do your best fighting with an evil organization. This content was copied from https://www.g2a.com/en/just-cause-4-steam-key-global-i10000156586001. It is protected by copyright, all rights reserved. If you want to use it, you are obligated to leave the link to the original source.',
        22, 4, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'jc4.jpg',
        'D:\springboot-training/upload/jc4.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'DOOM',
        'Doom (officially cased DOOM and occasionally DooM by fans, based on the Doom logo''s stylized lettering) is the first release of the Doom series, and one of the games that consolidated the first-person shooter genre. With a science fiction and horror style, it gives the players the role of marines who find themselves in the focal point of an invasion from Hell. The game introduced deathmatch and cooperative play in the explicit sense, and helped further the practice of allowing and encouraging fan-made modifications of commercial video games. It was first released on December 10, 1993, when a shareware copy was uploaded to an FTP server at the University of Wisconsin. The Ultimate Doom, an updated release of the original game featuring a fourth episode, was released in 1995 and sold at retail. In Doom, players assume the role of an unnamed space marine, who became popularly known as "Doomguy", fighting his way through hordes of invading demons from Hell. With one third of the game, nine levels, distributed as shareware, Doom was played by an estimated 15–20 million people within two years of its release, popularizing the mode of gameplay and spawning a gaming subculture. In addition to popularizing the FPS genre, it pioneered immersive 3D graphics, networked multiplayer gaming, and support for customized additions and modifications via packaged files in a data archive known as "WADs". As a sign of its effect on the industry, first-person shooter games from the genre''s boom in the 1990s, helped in no small part by the game''s release, became known simply as "Doom clones". Its graphic violence, as well as satanic imagery, made Doom the subject of considerable controversy.',
        19.99, 5, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'doom.jpg',
        'D:\springboot-training/upload/doom.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed Odyssey', 'Choose your fate in Assassin''s Creed® Odyssey.
From outcast to living legend, embark on an odyssey to uncover the secrets of your past and change the fate of Ancient Greece.
TRAVEL TO ANCIENT GREECE
From lush vibrant forests to volcanic islands and bustling cities, start a journey of exploration and encounters in a war torn world shaped by gods and men.
FORGE YOUR LEGEND
Your decisions will impact how your odyssey unfolds. Play through multiple endings thanks to the new dialogue system and the choices you make. Customize your gear, ship, and special abilities to become a legend.
FIGHT ON A NEW SCALE
Demonstrate your warrior''s abilities in large scale epic battles between Athens and Sparta featuring hundreds of soldiers, or ram and cleave your way through entire fleets in naval battles across the Aegean Sea.
GAZE IN WONDER
Experience the action in a whole new light with Tobii Eye Tracking. The Extended View feature gives you a broader perspective of the environment, and the Dynamic Light and Sun Effects immerse you in the sandy dunes according to where you set your sights. Tagging, aiming and locking on your targets becomes a lot more natural when you can do it by looking at them. Let your vision lead the way and enhance your gameplay.
Visit the Tobii website to check the list of compatible devices.', 59.99,
        5, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'asodyssey.jpg',
        'D:\springboot-training/upload/asodyssey.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Shadow of Mordor: Middle Earth', 'earth™: Shadow of Mordor™ now; continue their story October 10 in Middle-earth™: Shadow of War™.
Nemesis Forge available only with purchase of Middle-earth™: Shadow of Mordor™ and Middle-earth™: Shadow of War™ on PC and subject at all times to the WB Terms of Service. Nemesis Forge is not available in Japan.
Winner of over 50 "Best of 2014" Awards including Game of the Year, Best Action Game and Most Innovative Game.
More Missions: The Lord of the Hunt, The Bright Lord Story
More Challenges: Test of Power, Test of Speed, Test of Wisdom, Endless Challenge
More Skins: The Dark Ranger, Captain of the Watch
More Runes: Hidden Blade, Deadly Archer, Flame of Anor, Rising Storm
More Warband Missions: Guardians of the Flaming Eye, The Berserks, The Skull Crushers, The Flesh Burners, The Blood Hunters',
        30, 1, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'shadowm.jpg',
        'D:\springboot-training/upload/shadowm.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Ghost Recon: Wildlands', 'At the heart of the desolate Bolivia where the game takes place, the Ghost Recon Wildlands story surrounds the overpowering drug cartel called Santa Blanca.
Our beloved Ghosts have been sent behind enemy lines and into the heart of the terrorised, corrupted country to restore order and break ties between Santa Blanca and the government who have been letting them wreak havoc.
Bolivia was the perfect place for the franchise to take on the open-world genre and their recreation of this South American country is mesmerising. With gameplay teasers and trailers exploring the variety in landscapes that this daunting and exposed map has to offer.
This beautiful landscape has become the largest cocaine producer in the world. Taking on similar vibes to the popular TV series Narcos, being a protagonist at the centre of a bloody battle between power driven cartel giants and a lowly group of skilled men
', 17, 7,
        1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'grw.jpg',
        'D:\springboot-training/upload/grw.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product
VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Kingdom Come: Deliverance', 'It is set in the medieval Kingdom of Bohemia, an Imperial State of the Holy Roman Empire, with a focus on historically accurate content. The story takes place during a war in Bohemia in 1403. On the orders of Hungarian king Sigismund, Cuman mercenaries raid the mining village of Skalitz, a major source of silver.
', 14,
        2, 1);
INSERT INTO file_upload
VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'kcd.jpg',
        'D:\springboot-training/upload/kcd.jpg', currval('public.product_id_seq'));
COMMIT;