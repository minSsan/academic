# [CSE1017] 프로그래밍기초 기말고사
#
# 이름 : 박민선
# 학번 : 2020076735
#
################################################################
# 1. [5+5점] 게임 맵을 표현하고 그리기
################################################################
#


sample = [
    ["w","w","w","w","w","w"],
    ["w","P","B"," ","t","w"],
    ["w","w","w","w","w","w"],
]

test_map1 = [
    [" "," ","w","w","w"," "," "," "],
    [" "," ","w","t","w"," "," "," "],
    [" "," ","w"," ","w","w","w","w"],
    ["w","w","w","B"," ","B","t","w"],
    ["w","t"," ","B","P","w","w","w"],
    ["w","w","w","w","B","w"," "," "],
    [" "," "," ","w","t","w"," "," "],
    [" "," "," ","w","w","w"," "," "],
]

test_map2 = [
    ["w","w","w","w","w"," "," "," "," "],
    ["w"," "," "," ","w"," "," "," "," "],
    ["w"," ","B"," ","w"," ","w","w","w"],
    ["w"," ","B"," ","w"," ","w","t","w"],
    ["w","w","w"," ","w","w","w","t","w"],
    [" ","w","w"," ","P"," "," ","t","w"],
    [" ","w"," ","B"," ","w"," "," ","w"],
    [" ","w"," "," "," ","w","w","w","w"],
    [" ","w","w","w","w","w"," "," "," "],
]

# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def trans_cell(cell):
    word = []
    for i in range(len(cell)):
        if cell[i] == 'w':
            word.append('W')
        elif cell[i] == 'b':
            word.append('o')
        elif cell[i] == 'B':
            word.append('O')
        elif cell[i] == ' ':
            word.append(' ')
        elif cell[i] == 't':
            word.append('_')
        elif cell[i] == 'P':
            word.append('&')
        else:
            word.append('@')
    ans = ''
    for i in word:
        ans = ans + i
    return ans

# [v] 완성 | [ ] 부분 완성 | [] 미완성
def draw_map(map):
    for row in map:
        for word in trans_cell(row):
            print(word,end="")
        print()

################################################################
# 2. 창고지기 캐릭터 이동하기
################################################################
# [10] 창고지기 캐릭터의 이동 위치를 입력받는 함수 move_direction
# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def move_direction():
    direction = input("'a'(좌),'s'(하),'w'(상),'d'(우) 중 하나의 문자를 입력하세요.:")
    while direction not in ('a','s','w','d'):
        direction = input("'a'(좌),'s'(하),'w'(상),'d'(우) 중 하나의 문자를 입력하세요.:")
    return direction

# [10] 창고지기 캐릭터의 현재 위치를 알려주는 함수 find_player
# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def find_player(map): # map : 문자열의 리스트의 리스트(sample참고) == 게임맵
    length = len(map[0]) # 맵의 크기(=열의 길이)를 저장하는 변수 length
    for i in range(len(map)): # 행 번호 저장
        for j in range(length): # 열 번호 저장
            if map[i][j] == 'P' or map[i][j] == 'p':
                return (i,j)

# [10] 창고지기 캐릭터의 움직임을 맵에 반영하는 함수 mapping
# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def mapping(map, move): # move는 a,s,w,d 중 하나의 문자열임. (좌하상우)
    i, j = find_player(map) # 플레이어의 위치를 행, 열로 저장
    if move == 'a':
        if map[i][j-1] == " ": # 이동하려는 자리가 빈 공간이면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i][j-1] = "P" # 캐릭터는 빈 공간 위에 있는 캐릭터로 변경
            return map
        elif map[i][j-1] == "t": # 이동하려는 자리가 지정장소라면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i][j-1] = "p" # 캐릭터는 지정장소 위에 있는 캐릭터로 변경
            return map
        elif map[i][j-1] == "w":
            print("left is blocked by WALL.")
            return map
        elif map[i][j-1] == "b": # 이동하려는 자리에 지정 장소에 있는 상자가 있다면,
            if map[i][j-2] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i][j-2] == " ":
                map[i][j-2] = 'B'
                map[i][j-1] = 'p'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i][j-2] = 'b'
            map[i][j-1] = 'p'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
        elif map[i][j-1] == "B": # 이동하려는 자리에 빈 공간에 있는 상자가 있다면,
            if map[i][j-2] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i][j-2] == " ":
                map[i][j-2] = 'B'
                map[i][j-1] = 'P'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i][j-2] = 'b'
            map[i][j-1] = 'P'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
    
    if move == 'd':
        if map[i][j+1] == " ": # 이동하려는 자리가 빈 공간이면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i][j+1] = "P" # 캐릭터는 빈 공간 위에 있는 캐릭터로 변경
            return map
        elif map[i][j+1] == "t": # 이동하려는 자리가 지정장소라면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i][j+1] = "p" # 캐릭터는 지정장소 위에 있는 캐릭터로 변경
            return map
        elif map[i][j+1] == "w":
            print("right is blocked by WALL.")
            return map
        elif map[i][j+1] == "b": # 이동하려는 자리에 지정 장소에 있는 상자가 있다면,
            if map[i][j+2] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i][j+2] == " ":
                map[i][j+2] = 'B'
                map[i][j+1] = 'p'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i][j+2] = 'b'
            map[i][j+1] = 'p'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
        elif map[i][j+1] == "B": # 이동하려는 자리에 빈 공간에 있는 상자가 있다면,
            if map[i][j+2] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i][j+2] == " ":
                map[i][j+2] = 'B'
                map[i][j+1] = 'P'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i][j+2] = 'b'
            map[i][j+1] = 'P'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map

    if move == 'w':
        if map[i-1][j] == " ": # 이동하려는 자리가 빈 공간이면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i-1][j] = "P" # 캐릭터는 빈 공간 위에 있는 캐릭터로 변경
            return map
        elif map[i-1][j] == "t": # 이동하려는 자리가 지정장소라면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i-1][j] = "p" # 캐릭터는 지정장소 위에 있는 캐릭터로 변경
            return map
        elif map[i-1][j] == "w":
            print("upper side is blocked by the WALL.")
            return map
        elif map[i-1][j] == "b": # 이동하려는 자리에 지정 장소에 있는 상자가 있다면,
            if map[i-2][j] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i-2][j] == " ":
                map[i-2][j] = 'B'
                map[i-1][j] = 'p'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i-2][j] = 'b'
            map[i-1][j] = 'p'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
        elif map[i-1][j] == "B": # 이동하려는 자리에 빈 공간에 있는 상자가 있다면,
            if map[i-2][j] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i-2][j] == " ":
                map[i-2][j] = 'B'
                map[i-1][j] = 'P'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i-2][j] = 'b'
            map[i-1][j] = 'P'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
            
    if move == 's':
        if map[i+1][j] == " ": # 이동하려는 자리가 빈 공간이면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i+1][j] = "P" # 캐릭터는 빈 공간 위에 있는 캐릭터로 변경
            return map
        elif map[i+1][j] == "t": # 이동하려는 자리가 지정장소라면,
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            map[i+1][j] = "p" # 캐릭터는 지정장소 위에 있는 캐릭터로 변경
            return map
        elif map[i+1][j] == "w":
            print("lower side is blocked by WALL.")
            return map
        elif map[i+1][j] == "b": # 이동하려는 자리에 지정 장소에 있는 상자가 있다면,
            if map[i+2][j] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i+2][j] == " ":
                map[i+2][j] = 'B'
                map[i+1][j] = 'p'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i+2][j] = 'b'
            map[i+1][j] = 'p'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
        elif map[i+1][j] == "B": # 이동하려는 자리에 빈 공간에 있는 상자가 있다면,
            if map[i+2][j] in ('w', 'B', 'b'):
                print("There is an OBSTACLE in the space where the box will move.")
                return map
            if map[i+2][j] == " ":
                map[i+2][j] = 'B'
                map[i+1][j] = 'P'
                if map[i][j] == 'p':
                    map[i][j] = 't'
                else:
                    map[i][j] = ' '
                return map
            map[i+2][j] = 'b'
            map[i+1][j] = 'P'
            if map[i][j] == 'p':
                map[i][j] = 't'
            else:
                map[i][j] = ' '
            return map
            

################################################################
# 3. 메인 함수 작성
################################################################
# [10] 게임 종료 여부를 확인하는 보조함수 is_game_finished
# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def is_game_finished(map):
    for row in map:
        for word in row:
            if word == 'B':
                return False
    return True

# [10] 게임을 수행하는 메인 함수 sokoban
# [v] 완성 | [ ] 부분 완성 | [ ] 미완성
def sokoban(map):
    print("welcome to sokoban")
    while not is_game_finished(map):
        draw_map(map)
        direction = move_direction()
        mapping(map,direction)
    draw_map(map)
    print(
"\n=======================\n\
======= Finish! =======\n\
=======================\n")

################################################################
# 4. 게임 확장
################################################################
# [10] 한번에 여러 이동을 입력받도록 move_direction을 확장한 move_directions
# [ ] 완성 | [ ] 부분 완성 | [v] 미완성
def move_directions():
    def is_right_string(s): # 입력한 문자열이 'a', 's', 'd', 'w'로만 구성되어 있는지 검사하는 함수
        for word in s:
            if word not in ('a','s','d','w'):
                return False
        return True

    direction = input("'a'(좌),'s'(하),'w'(상),'d'(우)만 포함된 문자열을 입력하세요(공백X) :")
    while not is_right_string(direction):
        direction = input("'a'(좌),'s'(하),'w'(상),'d'(우)만 포함된 문자열을 입력하세요(공백X) :")
    return direction

# [10] 창고지기 캐릭터의 여러 움직임을 맵에 반영하는 함수 mapping2
# [ ] 완성 | [ ] 부분 완성 | [v] 미완성
def mapping2(map, moves):
    i, j = find_player(map)
    pass

# [10] 추가된 종료조건 함수 (움직일 수 없는 블록 탐지)
# [ ] 완성 | [ ] 부분 완성 | [v] 미완성
def no_more_move(map): # 더이상 옮길 수 없으면 True, 옮길 수 있으면 False를 반환
    for i in range(len(map)):
        for j in range(len(map[0])):
            if map[i][j] == 'B':    
                if (map[i-1][j] == 'w' or map[i+1][j] == 'w') and (map[i][j-1] == 'w' or map[i][j+1] == 'w'):
                # 연속한 두 면이 벽으로 둘러싸인 경우
                    return True # 상자를 더이상 옮길 수 없음
                return (map[i-1][j] == 'w' and map[i+1][j] == 'w' and (map[i][j-1] == 'w' or map[i][j+1] == 'w')) or\
                (map[i][j-1] == 'w' and map[i][j+1] == 'w' and (map[i-1][j] == 'w' or map[i+1][j] == 'w'))
# 확장 함수를 사용한 메인함수 sokoban2
# [ ] 완성 | [ ] 부분 완성 | [v] 미완성
def sokoban2(map):
    print("welcome to sokoban")
    draw_map(map)
    while not is_game_finished(map):
        directions = move_directions()
        for direction in directions:
            mapping(map,direction)
        draw_map(map)
        if no_more_move(map):
            # print message for no_more_move
            print("You can't move the boxes anymore.")
            break
    if is_game_finished(map):
        print("======== Congratulations! You moved all boxes. ========")
    # print message for end of game

################################################################
#
# 아래 준비된 게임 맵으로 테스트 해 보세요.
#

def test_sample():
    sokoban(sample)

def test1():
    sokoban(test_map1)

def test2():
    sokoban(test_map2)

sample = [
    ["w","w","w","w","w","w"],
    ["w","P","B"," ","t","w"],
    ["w","w","w","w","w","w"],
]

test_map1 = [
    [" "," ","w","w","w"," "," "," "],
    [" "," ","w","t","w"," "," "," "],
    [" "," ","w"," ","w","w","w","w"],
    ["w","w","w","B"," ","B","t","w"],
    ["w","t"," ","B","P","w","w","w"],
    ["w","w","w","w","B","w"," "," "],
    [" "," "," ","w","t","w"," "," "],
    [" "," "," ","w","w","w"," "," "],
]

test_map2 = [
    ["w","w","w","w","w"," "," "," "," "],
    ["w"," "," "," ","w"," "," "," "," "],
    ["w"," ","B"," ","w"," ","w","w","w"],
    ["w"," ","B"," ","w"," ","w","t","w"],
    ["w","w","w"," ","w","w","w","t","w"],
    [" ","w","w"," ","P"," "," ","t","w"],
    [" ","w"," ","B"," ","w"," "," ","w"],
    [" ","w"," "," "," ","w","w","w","w"],
    [" ","w","w","w","w","w"," "," "," "],
]
sokoban2(test_map2)