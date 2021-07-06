<template>
  <section class="section">
    <div class="columns is-mobile">
       <b-field label="Number of Disks">
            <b-input v-model="numDisks"></b-input>
            <p class="control">
                <b-button class="button is-primary"  @click="go">Go</b-button>
            </p>
        </b-field>
    </div>
    <div class="columns mt-4" v-if="states && states.length">
      <b-button type="is-primary is-light" :disabled="this.statePointer == 0" @click="prevState">Prev</b-button>
      <b-button type="is-primary is-light" :disabled="this.statePointer == this.states.length - 1" @click="nextState">Next</b-button>
    </div>
    <div class="columns rods" v-if="states && states.length">
      <div class="column">
        <div class="columns">
          <div class="rod">
            <div v-for="disk in firstTower" :key="disk.diskId" :style="disk.style" class="disk">
                {{disk.diskId}}
            </div>
          </div>
          <div class="rod">
            <div v-for="disk in secondTower" :key="disk.diskId" :style="disk.style" class="disk">
                {{disk.diskId}}
            </div>
          </div>
          <div class="rod">
            <div v-for="disk in thirdTower" :key="disk.diskId" :style="disk.style" class="disk">
                {{disk.diskId}}
            </div>
          </div>
        </div>
      </div>
      <div class="column instructions">
        <h1 class="h1 is-size-3"> Instructions</h1>
        <ul>
          <li v-for="n in statePointer">
            {{states[n].instruction}}
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>

<script>
import distinctColors from 'distinct-colors'

const DISK_MAX_WIDTH = 150;
const BOTTOM = 48;
const DISK_HEIGHT = 30;
const ROD_WIDTH = 30;
const COLORS = distinctColors({count: 10})

function buildTower(n, disks) {
  const diskMinWidth = DISK_MAX_WIDTH / 2;
  const diskWidthIncrements = diskMinWidth / n;
  const result = [];
  
  for (let i = 0; i < disks.length; i++) {
    const diskWidth = diskMinWidth + disks[i] * diskWidthIncrements
    const diskId = disks[i]

    result.push({
      diskId,
      style: {
        backgroundColor: COLORS[(diskId - 1)].hex(),
        bottom: BOTTOM + (DISK_HEIGHT * i) + 'px',
        width: diskWidth + 'px',
        marginLeft: '-' + (diskWidth - ROD_WIDTH)/2 +'px'
      }
    });
  }

  return result;
}

export default {
  name: 'HomePage',

  components: {

  },

  computed: {
    firstTower() {
      const state = this.states[this.statePointer].stacks[0]
      buildTower(this.n, state)
      
      return buildTower(this.n, state)
    },

    secondTower() {
      const state = this.states[this.statePointer].stacks[1]
      return buildTower(this.n, state)
    },

    thirdTower() {
      const state = this.states[this.statePointer].stacks[2]
      return buildTower(this.n, state)
    }
  },

  methods: {
    async go() {
      if (!this.numDisks) {
        return;
      }
      this.statePointer = 0;
      const getStatesResult = await this.$axios.get('/towerofhanoi?n=' + this.numDisks)
      this.n = this.numDisks
      this.states = getStatesResult.data.data
    },

    nextState() {
      if (this.statePointer < this.states.length - 1) {
        this.statePointer ++
      }
      const container = this.$el.querySelector(".instructions");
      container.scrollTop = container.scrollHeight;
    },

    prevState() {
      if (this.statePointer > 0) {
        this.statePointer --;
      }
      const container = this.$el.querySelector(".instructions");
      container.scrollTop = container.scrollHeight;
    }
  },

  data() {
    return {
      n: 3,
      numDisks: 3,
      states: null,
      statePointer: 0,
    }
  }
}
</script>

<style scoped>
  .rod {
    width: 30px;
    height: 300px;
    background-color: brown;
    margin-top: 30px;
    margin-left: 250px;
  }

  .rods {
    margin-left: -200px;
  }

  .instructions {
    padding-left: 150px;
    height:300px;
    overflow: auto;
  }

  .disk {
    background-color: black;
    height: 30px;
    position: absolute;
    bottom: 48px;
    text-align: center;
    color: white;
  }
</style>
