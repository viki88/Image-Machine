package com.vikination.imagemachine.ui.home;

import com.vikination.imagemachine.model.Machine;

interface OnClickMachineItemListener {
    void onClickMachine(Machine machine);
    void onClickEditMachine(Machine machine);
    void onClickDeleteMachine(Machine machine);
}
