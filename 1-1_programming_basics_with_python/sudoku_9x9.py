#
# 프로그래밍기초 과제 #06
#
# 학번 : 2020076735
# 이름 : 박민선
#

def initialize_board_9x9():
    from random import shuffle
    row0 = [1,2,3,4,5,6,7,8,9]
    shuffle(row0)
    row1 = row0[3:9] + row0[0:3]
    row2 = row1[3:9] + row1[0:3]
    row3 = row0[1:3] + [row0[0]] + row0[4:6] + [row0[3]] + row0[7:9] + [row0[6]]
    row4 = row3[3:9] + row3[0:3]
    row5 = row4[3:9] + row4[0:3]
    row6 = [row0[2]] + row0[0:2] + [row0[5]] + row0[3:5] + [row0[8]] + row0[6:8]
    row7 = row6[3:9] + row6[0:3]
    row8 = row7[3:9] + row7[0:3]
    return [row0,row1,row2,row3,row4,row5,row6,row7,row8]

def shuffle_ribbons(board):
    from random import shuffle
    top = board[0:3]
    middle = board[3:6]
    bottom = board[6:9]
    shuffle(top)
    shuffle(middle)
    shuffle(bottom)
    return top + middle + bottom

def transpose(board):
    transposed = [[] for _ in range(len(board))]
    for j in range(len(board)):
        for i in range(len(board)):
            transposed[j].append(board[i][j])
    return transposed

def ans_board_9x9():
    board = initialize_board_9x9()
    board = shuffle_ribbons(board)
    board = transpose(board)
    board = shuffle_ribbons(board)
    board = transpose(board)
    return board

def copy_board(board):
    cpy = []
    for row in board:
        cpy.append(row[:])
    return cpy

def show_board(board):
    print("S | 1 2 3 4 5 6 7 8 9")
    print("- - - - - - - - - - -")
    for row in range(len(board)):
        print(row+1,end=" ")
        print("|",end=" ")
        for i in board[row]:
            if i == 0:
                print(".",end=" ")
            else:
                print(i,end=" ")
        print()

def choice_level():
    level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 :")
    while level not in ('1','2','3'):
        level = input("난이도(초급 1, 중급 2, 고급 3)를 숫자로 입력해주세요 :")
    if level == '1':
        return 27
    elif level == '2':
        return 40
    else:
        return 54

def make_holes(board,holes):
    from random import randint
    while holes > 0:
        i = randint(0,8)
        j = randint(0,8)
        if board[i][j] != 0:
            board[i][j] = 0
            holes -= 1
    return board

def get_integer(message,i,j):
    num = input(message)
    while not(num.isdigit() and 1 <= int(num) <= 9):
        num = input(message)
    return int(num)

def sudoku_mini():
    ans_board = ans_board_9x9()
    user_board = copy_board(ans_board)
    holes = choice_level()
    user_board = make_holes(user_board,holes)
    show_board(user_board)
    while holes > 0:
        row = get_integer("가로줄번호(1~9):",1,9) - 1
        col = get_integer("세로줄번호(1~9):",1,9) - 1
        if user_board[row][col] != 0:
            print("빈칸이 아닙니다.")
            continue
        answer = int(input("숫자(1~9):"))
        if ans_board[row][col] == answer:
            user_board[row][col] = answer
            holes -= 1
            show_board(user_board)
        else:
            print(str(answer)+"가(이) 아닙니다. 다시 해보세요.")
    print("잘 하셨습니다. 또 들리세요.")

sudoku_mini()