import storm
class bolt(storm.BasicBolt):
    def initialize(self, stormconf, context):
        self._conf=stormconf
        self._context=context
    def process(self, tuple):
        word=tuple.values[0]
        storm.emit([word,len(word)])
