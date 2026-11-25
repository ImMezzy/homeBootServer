INSERT INTO tournaments (
    id, created_at, updated_at, title, description,
    max_players, prize_fund, starts_at, status
) VALUES
    ('11111111-1111-4111-8111-111111111111', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Киберспортивный открытый кубок', 'Индивидуальный турнир с посевом игроков по рейтингу.',
     16, 30000.00, TIMESTAMP WITH TIME ZONE '2027-06-15 18:00:00+03', 'REGISTRATION'),
    ('22222222-2222-4222-8222-222222222222', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Студенческий киберспортивный кубок', 'Индивидуальный турнир среди студентов.',
     8, 15000.00, TIMESTAMP WITH TIME ZONE '2027-07-20 18:00:00+03', 'REGISTRATION'),
    ('33333333-3333-4333-8333-333333333333', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Тестовый турнир', 'Турнир без участников для проверки удаления.',
     8, 1000.00, TIMESTAMP WITH TIME ZONE '2027-08-20 18:00:00+03', 'REGISTRATION');

INSERT INTO players (
    id, created_at, updated_at, username, rating, seed, tournament_id
) VALUES
    ('aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Alex', 1800, NULL, '11111111-1111-4111-8111-111111111111'),
    ('aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Max', 1500, NULL, '11111111-1111-4111-8111-111111111111'),
    ('aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Kate', 1650, NULL, '22222222-2222-4222-8222-222222222222'),
    ('aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'Sam', 1300, NULL, '22222222-2222-4222-8222-222222222222'),
    ('aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
     'TestPlayer', 1400, NULL, '11111111-1111-4111-8111-111111111111');
