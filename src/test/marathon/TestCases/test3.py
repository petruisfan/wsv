#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_25")
    if window('WServer'):
        click('Start')
        select('Switch to maintenance mode', 'true')
        click('...')

        if window('Open'):
            select('JFileChooser_4', '/data')
        close()

        assert_p('lbl:/data', 'Text', '/data')
        click('Set')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'This is not a number!')
            click('OptionPane.button')
        close()

        select('Port', '2000')
        click('Set')
        assert_p('lbl:2000', 'Text', '2000')
        click('Stop')
    close()

    pass