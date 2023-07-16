use `quintor-main`;

SET FOREIGN_KEY_CHECKS=0;
INSERT INTO users 
    (user_id, email, password, role)
VALUES
    (1, 'jan@gmail.com', 'test', 'user'),
    (2, 'pedro@gmail.com', 'test', 'admin'),
	(3, 'peter@gmail.com', 'test', 'graduate');

INSERT INTO events
    (event_id, user_id, title, description, briefDescription, startDateTime, location, isApproved, isGuestEnabled, isGraduateChecked)
VALUES
    (1, 2, 'Feestavond: Terug in de tijd', 'Stap in de tijdmachine en reis terug naar de jaren ''80 tijdens onze nostalgische feestavond. Trek je beste retro-outfit aan, dans op de grootste hits van toen en herleef de magie van een tijdperk vol neonkleuren en dansvloeren. Laat je meeslepen door de nostalgie en beleef een onvergetelijke avond!', 'Een nostalgische avond vol muziek, dans en plezier', '2023-06-20 19:00:00', 'Quintor, Groningen', 1, 1, 0),
    (2, 2, 'Creatieve Workshop: Verven met Acrylverf', 'Ontsnap aan de dagelijkse sleur en geef je creativiteit de vrije loop tijdens onze schilderworkshop. Leer verschillende technieken, experimenteer met kleuren en creëer je eigen meesterwerk onder begeleiding van een ervaren kunstenaar. Geen ervaring vereist!', 'Ontdek je artistieke kant tijdens deze inspirerende workshop', '2023-06-03 14:30:00', 'Kunststudio XYZ', 1, 0, 0),
    (3, 2, 'Bedrijfsbarbecue', 'Kom en geniet van een ontspannen middag vol lekker eten, leuke activiteiten en de kans om je collega''s beter te leren kennen. Vergeet niet je favoriete gerecht mee te nemen om te delen!', 'Samen genieten van heerlijk eten en goed gezelschap', '2023-06-21 16:00:00', 'Stadspark', 1, 0, 0),
    (4, 2, 'Bordspellendag', 'Een leuke middag vol bordspellen, lekker hapjes en gezelligheid. Ben jij de ultieme monopoly kampioen of misschien blink je wel uit in Kolonisten!', 'Een leuke middag met collega''s en eventuele aanhang bordspellen spelen', '2023-08-19 20:00:00', 'Quintor, Deventer', 1, 1, 0),
	(5, 1, 'Skiweekend', 'Een weekend met collega''s skiën in de prachtige Alpen, wie wilt dit nou missen? Misschien kan jij je wel ontknopen tot de beste skiër van het bedrijf! Geen fan van skiën maar wel van de versie die ''s avonds en binnen wordt gehouden? Ook geen probleem, misschien ben jij wel de koning van de apreski?', 'Een gezellig skiweekend met collega''s naar de Alpen', '2024-01-06 10:00:00', 'Quintor, Deventer', 0, 0, 0),
	(6, 1, 'Afstudeerder BBQ', 'De afstudeerstages van 2023 zijn weer bijna afgelopen. Om iedereen die bij ons deze stage heeft gelopen te bedanken is er een BQQ voor afsudeerders georganiseerd!', 'Een gezellige BQQ voor de afstudeerders', '2023-06-24 19:00:00', 'Quintor, Deventer', 0, 0, 1),
    (7, 2, 'Buffet bij Quintor!', 'Een warm en koud buffet georganiseerd voor alle medewerkers van Quintor!', 'Buffet bij Quintor voor alle medewerkers', '2023-04-15 21:00:00', 'Quintor, Den Bosch', 1, 0, 0);

INSERT INTO registrations
	(registration_id, event_id, user_id, note)
VALUES
	(1, 3, 1, 'Momenteel loop ik rond met een gebroken been, hoewel dit waarschijnlijk weinig invloed heeft, vondt ik het toch handig om dit even te melden zodat jullie hiervan op de hoogte zijn.');
    
INSERT INTO diets
	(name)
VALUES 
    ('Glutenvrij'), ('Lactosevrij'), ('Pindavrij'), ('Halal'), ('Kosher'), ('Eivrij'), ('Visvrij'),
    ('Sojavrij'), ('Selderijvrij'), ('Sesamvrij'), ('Sulfietvrij'), ('Schaaldiervrij'), ('Schelpdierenvrij'),
    ('Weekdiervrij'), ('Lupinevrij'), ('Mosterdvrij'), ('Notenvrij'), ('Veganistisch'), ('Vegetarisch'), ('Pescotarisch');

INSERT INTO user_diets
	(user_id, diet_id)
VALUES
	(2, 1),
    (2, 2),
    (2, 3),
    (2, 4);

INSERT INTO ideas
	(user_id, idea, likes)
VALUES
	(1, "Zouden mensen het eens leuk vinden om eens een dagje naar zee te gaan?", 0);

INSERT INTO event_feedback
    (event_id, user_id, feedback)
VALUES
    (7, 1, "Opties in het buffet waren naar mijn mening uitstekend en mogen zeker vaker terugkomen indien dit vaker wordt georganiseerd! Sommige dingen waren daarentegen wel een beetje koud dus daar de volgende keer op letten");

SET FOREIGN_KEY_CHECKS=1;
