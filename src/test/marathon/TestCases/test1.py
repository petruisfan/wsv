#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_25")
    if window('WServer'):
        click('Start')
        assert_p('lbl:running', 'Text', 'running')
        select('Switch to maintenance mode', 'true')
        assert_p('lbl:maintenance', 'Text', 'maintenance')
        select('Switch to maintenance mode', 'false')
        assert_p('lbl:running', 'Text', 'running')
        click('Stop')
        assert_p('lbl:stopped', 'Text', 'stopped')
    close()
